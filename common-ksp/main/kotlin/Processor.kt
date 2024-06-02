package ai.sider.common.ksp

import com.meowool.reflectify.common.cast
import com.google.devtools.ksp.processing.CodeGenerator
import com.google.devtools.ksp.processing.KSPLogger
import com.google.devtools.ksp.processing.Resolver
import com.google.devtools.ksp.processing.SymbolProcessor
import com.google.devtools.ksp.processing.SymbolProcessorEnvironment
import com.google.devtools.ksp.processing.SymbolProcessorProvider
import com.google.devtools.ksp.symbol.KSAnnotated

/**
 * A KSP processor wrapper that provides a simpler API for creating code generation tasks.
 * See [Processor.Builder] and [Codegen] for more details.
 */
abstract class Processor internal constructor(
  private val logger: KSPLogger,
  private val codeGenerator: CodeGenerator,
  private val namespace: String,
  private val codegenList: Array<Class<*>>,
) : SymbolProcessor {
  // Store codegen for multi-round processing (Class, (Codegen, CombinedSymbols))
  private val pending = mutableMapOf<Class<*>, Pair<Codegen<Any>, Any?>>()

  override fun process(resolver: Resolver): List<KSAnnotated> {
    codegenList.forEach {
      val (codegen, prev) = pending.getOrElse(it) {
        try {
          it.getDeclaredConstructor()
            .newInstance()
            .cast<Codegen<Any>>()
            .apply { context = Context(logger, resolver, namespace, codeGenerator) } to null
        } catch (e: Exception) {
          logger.error("The Codegen class ${it.simpleName} must have a no-arg constructor.")
          return emptyList()
        }
      }
      // 1. We collect symbols for this round of processing
      val data = codegen.collectSymbols()
      // 2. Run the incremental stage if needed
      if (codegen.enterStage.needIncremental) codegen.incrementalStage(data)
      // 3. If this codegen still needs to enter the final stage, we need to combine the data
      if (codegen.enterStage.needFinal) {
        // 4. Combine this round with the previous round of data
        val combined = when (prev) {
          null -> data
          else -> codegen.combineSymbols(prev, data)
        }
        // 5. Update the current generated data
        pending[it] = codegen to combined
      }
    }

    return emptyList()
  }

  override fun finish() {
    super.finish()
    // Continue to run the final stage of the pending codegen
    pending.values.forEach { (codegen, combined) -> codegen.finalStage(combined!!) }
  }

  /**
   * A builder class that helps create instances of the [Processor] class.
   *
   * This builder provides a fluent API for configuring the different code generation tasks of the
   * processor.
   */
  class Builder {
    @PublishedApi
    internal val codegen = mutableListOf<Class<*>>()

    /**
     * Adds a code generation task to be executed.
     *
     * The [Codegen] instance must have a no-arg constructor.
     *
     * @param T The type of the [Codegen] instance to be added.
     * @return This [Builder] instance for chaining.
     */
    inline fun <reified T : Codegen<*>> with() = apply {
      this.codegen.add(T::class.java)
    }

    /**
     * Builds the [Processor] instance with the provided code generation tasks.
     *
     * @param environment The [SymbolProcessorEnvironment] to be used.
     *   See [SymbolProcessorProvider.create] for more details.
     */
    fun build(environment: SymbolProcessorEnvironment): Processor = object : Processor(
      logger = environment.logger,
      codeGenerator = environment.codeGenerator,
      namespace = requireNotNull(environment.options["ksp.namespace"]) {
        "'ksp.namespace' must be specified in the KSP arguments."
      },
      codegenList = codegen.toTypedArray()
    ) {}
  }
}
