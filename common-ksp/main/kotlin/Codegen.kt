package ai.sider.common.ksp

/**
 * An abstract class that represents a code generation that can participate in different stages
 * of the Kotlin Symbol Processing (KSP) process.
 *
 * Subclasses of this class should implement the specific logic for their code generation tasks.
 *
 * The main responsibilities of the task include:
 * 1. Collecting the necessary symbols for code generation.
 * 2. Combining the symbols collected in each incremental stage. (Optional)
 * 3. Performing any necessary processing on the collected symbols during the incremental and final stages.
 *
 * @property enterStage The stage at which this code generation task should be executed.
 */
abstract class Codegen<SymbolsCollection : Any>(val enterStage: ProcessStage) {
  /**
   * The context of the KSP process.
   */
  lateinit var context: Context

  /**
   * Collects the symbols that this [Codegen] instance will work with.
   *
   * Subclasses must implement this function to gather the necessary symbols for their code
   * generation task.
   *
   * @return The collection of symbols that this [Codegen] instance will process.
   */
  abstract fun collectSymbols(): SymbolsCollection

  /**
   * Combines the symbols collected in each incremental stage.
   *
   * If a subclass needs to enter the final stage, it usually needs to override this function
   * to customize the combination logic. If necessary, it should also de-duplicate during
   * the combination.
   *
   * The result of the last combination will be used as the input for the [finalStage].
   *
   * @param a The first set of collected symbols.
   * @param b The second set of collected symbols.
   * @return The combined set of symbols.
   */
  open fun combineSymbols(a: SymbolsCollection, b: SymbolsCollection): SymbolsCollection = b

  /**
   * Performs any necessary incremental processing on the collected symbols.
   *
   * If [enterStage] is [ProcessStage.Incremental] or [ProcessStage.All], this function
   * is executed during the incremental stage of the KSP process.
   *
   * The default implementation does not perform any operation, but subclasses can override
   * this function to perform additional processing on the symbols during the incremental stage
   * of the KSP process.
   *
   * @param collected The collected symbols.
   */
  open fun incrementalStage(collected: SymbolsCollection) = Unit

  /**
   * Performs any necessary final processing on the combined symbols.
   *
   * If [enterStage] is [ProcessStage.Final] or [ProcessStage.All], this function
   * is executed during the final stage of the KSP process.
   *
   * The default implementation does not perform any operation, but subclasses can
   * override this function to perform additional processing on the symbols during
   * the final stage of the KSP process.
   *
   * @param combined The combined set of symbols, see [combineSymbols].
   */
  open fun finalStage(combined: SymbolsCollection) = Unit

  /**
   * Represents the different stages of the code generation process.
   */
  enum class ProcessStage {
    /**
     * Indicates the incremental stage of the KSP process. This may result in multiple calls
     * to [Codegen.incrementalStage].
     *
     * This stage is typically used to generate code that only depends on a single file.
     *
     * If the code generation requires collecting all symbols in the module first, the [Final]
     * stage should be used instead.
     */
    Incremental,

    /**
     * Indicates the final stage of the KSP process.
     *
     * This stage is typically used to generate code that depends on all symbols in the module.
     *
     * If the code generation only depends on a single file, the [Incremental] stage can
     * be used instead.
     */
    Final,

    /**
     * Indicates that both the [Incremental] and [Final] stages should be executed.
     */
    All;

    /**
     * Indicates whether the current [ProcessStage] requires the incremental stage to be
     * executed.
     *
     * @see Codegen.incrementalStage
     */
    val needIncremental: Boolean get() = this == Incremental || this == All

    /**
     * Indicates whether the current [ProcessStage] requires the final stage to be
     * executed.
     *
     * @see Codegen.finalStage
     */
    val needFinal: Boolean get() = this == Final || this == All
  }
}
