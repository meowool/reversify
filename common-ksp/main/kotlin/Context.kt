package ai.sider.common.ksp

import com.google.devtools.ksp.processing.CodeGenerator
import com.google.devtools.ksp.processing.KSPLogger
import com.google.devtools.ksp.processing.Resolver

/**
 * Represents the context of the process of KSP.
 *
 * This data class provides a centralized way to access various components of the KSP process,
 * such as the logger, resolver, namespace, and code generator.
 *
 * @property logger The [KSPLogger] instance used for logging messages.
 * @property resolver The [Resolver] instance used for resolving symbols.
 * @property namespace The namespace of the current module.
 * @property codeGenerator The [CodeGenerator] instance used for generating code.
 */
data class Context(
  private val logger: KSPLogger,
  private val resolver: Resolver,
  val namespace: String,
  val codeGenerator: CodeGenerator,
) : Resolver by resolver, KSPLogger by logger {
  /**
   * The name of the current module, extracted from the namespace.
   */
  val moduleName = namespace.substringAfterLast('.')

  /**
   * The package name of the current module, represented as a [PackageName] instance.
   */
  val packageName = PackageName(namespace)
}
