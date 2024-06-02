package ai.sider.common.ksp

import com.google.devtools.ksp.symbol.KSAnnotated
import com.google.devtools.ksp.symbol.KSAnnotation
import com.google.devtools.ksp.symbol.KSClassDeclaration
import com.google.devtools.ksp.symbol.KSDeclaration
import com.google.devtools.ksp.symbol.KSFile
import com.google.devtools.ksp.symbol.KSFunctionDeclaration
import com.google.devtools.ksp.symbol.KSType
import com.google.devtools.ksp.symbol.KSTypeReference
import com.squareup.kotlinpoet.AnnotationSpec
import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.CodeBlock
import com.squareup.kotlinpoet.FileSpec
import com.squareup.kotlinpoet.FunSpec
import com.squareup.kotlinpoet.MemberName
import com.squareup.kotlinpoet.buildCodeBlock
import com.squareup.kotlinpoet.ksp.originatingKSFiles
import com.squareup.kotlinpoet.ksp.writeTo

/**
 * Returns a new [ClassName] instance representing the companion object in this class.
 */
val ClassName.companionObject: ClassName get() = nestedClass("Companion")

/**
 * Retrieves the parent [KSClassDeclaration] of the current [KSDeclaration], if it exists.
 *
 * This can be useful when you need to access the parent class declaration of a specific
 * [KSDeclaration], such as a function or property declaration.
 */
val KSDeclaration?.parentClass: KSClassDeclaration?
  get() = when (this?.parentDeclaration) {
    is KSClassDeclaration -> this.parentDeclaration as KSClassDeclaration
    else -> null
  }

/**
 * Checks if the [Sequence] of [KSTypeReference] contains a type with the specified [ClassName].
 *
 * Returns `true` if the sequence contains a type that has the same qualified name as the given
 * [ClassName], `false` otherwise.
 */
operator fun Sequence<KSTypeReference>.contains(className: ClassName) = any {
  it.resolve().declaration.qualifiedName?.asString() == className.canonicalName
}

/**
 * Checks if the current [KSTypeReference] matches the specified `other` object.
 *
 * The matching logic is as follows:
 *
 * - If both `this` and `other` are `null`, returns `true`.
 *
 * - If either `this` or `other` is `null`, returns `false`.
 *
 * - If `other` is a [ClassName], returns `true` if the qualified name of the resolved
 *   declaration matches the canonical name of the [ClassName].
 *
 * - If `other` is a [KSDeclaration], returns `true` if the qualified name of the resolved
 *   declaration matches the qualified name of `other`.
 *
 * - If `other` is a [KSTypeReference], recursively checks if the resolved types match.
 *
 * - If `other` is a [KSType], checks if the arguments of the types match.
 *
 * - For any other type of `other`, returns `false`.
 *
 * @param other The object to compare the current [KSTypeReference] against.
 */
fun KSTypeReference?.matches(other: Any?): Boolean {
  if (this == null && other == null) return true
  if (this == null || other == null) return false
  val aType = resolve()
  val a = aType.declaration
  val b = when (other) {
    is ClassName -> return a.qualifiedName?.asString() == other.canonicalName
    is KSDeclaration -> other
    is KSTypeReference -> return matches(other.resolve())
    is KSType -> {
      if (other.arguments.size != aType.arguments.size) return false
      if (other.arguments.isNotEmpty()) {
        val aArgs = aType.arguments.map { it.type }
        val bArgs = other.arguments.map { it.type }
        if (aArgs.zip(bArgs).any { (a, b) -> !a.matches(b) }) return false
      }
      other.declaration
    }
    else -> return false
  }
  return a.qualifiedName?.asString() == b.qualifiedName?.asString()
}

/**
 * Checks if this type matches the specified [other] type, considering the nullability of
 * the types.
 *
 * For example, if this is `String?` (nullable) and other is `String` (non-nullable), it
 * returns `false`.
 *
 * @param other The [KSType] to compare the current [KSType] against.
 */
fun KSType?.matchesNullable(other: KSType?): Boolean {
  if (this == null && other == null) return true
  if (this == null || other == null) return false
  if (this.isMarkedNullable != other.isMarkedNullable) return false
  return true
}

/**
 * Converts the [KSFunctionDeclaration] to a [MemberName], which includes the package name
 * (and class name, if any) and the simple name of the function.
 */
fun KSFunctionDeclaration.toMemberName() = MemberName(
  packageName = parentDeclaration!!.qualifiedName!!.asString(),
  simpleName = simpleName.asString()
)

/**
 * Adds the `INVISIBLE_REFERENCE` and `INVISIBLE_MEMBER` annotation arguments to the
 * [AnnotationSpec.Builder].
 */
fun AnnotationSpec.Builder.addInvisibleSuppress() = this
  .addMember("%S", "INVISIBLE_REFERENCE")
  .addMember("%S", "INVISIBLE_MEMBER")

/**
 * Validates the annotations on a sequence of [KSDeclaration] instances.
 *
 * @param annotation The [ClassName] of the annotation to validate.
 * @param predicate The predicate function to check if the annotation is valid for the declaration.
 */
context(Codegen<*>)
fun <T : KSDeclaration> Sequence<T>.validateAnnotation(
  annotation: ClassName,
  predicate: (T) -> Boolean,
) = onEach {
  require(predicate(it)) { "Cannot annotate '${it.logName()}' with '@${annotation.canonicalName}'" }
  context.logging("Found symbol", symbol = it)
}

/**
 * Returns the qualified name or simple name of the [KSDeclaration] as a string.
 */
fun KSDeclaration.logName() = qualifiedName?.asString() ?: simpleName.asString()

/**
 * Returns the name of the [KSTypeReference] as a string, including any type arguments and
 * nullability.
 */
fun KSTypeReference?.logName(): String = buildString {
  val type = this@logName?.resolve() ?: return@buildString
  append(type.declaration.simpleName.asString())
  if (type.arguments.isNotEmpty()) {
    append("<")
    append(type.arguments.joinToString { it.type!!.logName() })
    append(">")
  }
  if (type.isMarkedNullable) append("?")
}

/**
 * Finds the first [KSAnnotation] with the specified [ClassName] on the [KSAnnotated] element.
 *
 * @param className The [ClassName] of the annotation to find.
 * @return The [KSAnnotation] instance, or `null` if not found.
 */
fun KSAnnotated.findAnnotation(className: ClassName) = annotations.find {
  it.shortName.asString() == className.simpleName && it.annotationType.resolve()
    .declaration.qualifiedName?.asString() == className.canonicalName
}

/**
 * Finds the argument with the specified name in the [KSAnnotation].
 *
 * @param name The name of the argument to find.
 * @return The argument value, or `null` if not found.
 */
fun KSAnnotation?.findArgument(name: String) = this?.arguments?.find {
  it.name?.asString() == name
}

/**
 * Finds the string argument with the specified name in the [KSAnnotation].
 *
 * @param name The name of the argument to find.
 * @return The string value of the argument, or `null` if not found or empty.
 */
fun KSAnnotation?.findStringArgument(name: String) =
  when (val value = findArgument(name)?.value as? String) {
    null -> null
    else -> value.takeIf { it.isNotEmpty() }
  }

/**
 * Writes the [FileSpec] to the code generator, using the provided originating declarations.
 *
 * @param originatingDeclarations The [Iterable] of [KSDeclaration] objects that originated the
 *   code being generated.
 */
context(Codegen<*>)
fun FileSpec.write(originatingDeclarations: Iterable<KSDeclaration>) =
  write(originatingDeclarations.mapNotNull { it.containingFile }.toSet())

/**
 * Writes the [FileSpec] to the code generator, using the provided originating declaration.
 *
 * @param originating The [KSDeclaration] object that originated the code being generated.
 */
context(Codegen<*>)
fun FileSpec.write(originating: KSDeclaration) = write(listOf(originating.containingFile!!))

/**
 * Writes the [FileSpec] to the code generator, using the provided originating KSFiles.
 *
 * @param originatingFiles The [Iterable] of [KSFile] objects that originated the code being
 *   generated. If not provided, the [originatingKSFiles] function will be used to retrieve the
 *   originating files.
 */
context(Codegen<*>)
@JvmName("writeWithFiles")
fun FileSpec.write(originatingFiles: Iterable<KSFile> = originatingKSFiles()) =
  writeTo(context.codeGenerator, aggregating = true, originatingFiles)

/**
 * Adds a [CodeBlock] to the [FunSpec.Builder] using the provided [builderAction].
 *
 * @see buildCodeBlock
 */
inline fun FunSpec.Builder.addCode(builderAction: CodeBlock.Builder.() -> Unit): FunSpec.Builder =
  addCode(buildCodeBlock(builderAction))
