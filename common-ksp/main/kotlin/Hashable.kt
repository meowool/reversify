package ai.sider.common.ksp

import com.meowool.reflectify.common.EqualsBuilder
import com.meowool.reflectify.common.HashCodeBuilder
import com.meowool.reflectify.common.identityHashCode
import com.google.devtools.ksp.symbol.KSClassDeclaration
import com.google.devtools.ksp.symbol.KSFunctionDeclaration

/**
 * A wrapper around the [KSClassDeclaration] interface that provides hashcode and equality
 * semantics.
 *
 * This class allows [KSClassDeclaration] instances to be used as keys in hash-based data
 * structures, such as [HashMap]s and [HashSet]s.
 */
class HashableClass(private val inner: KSClassDeclaration) : KSClassDeclaration by inner {
  override fun equals(other: Any?): Boolean {
    if (this === other) return true
    if (other !is HashableClass) return false
    return inner.qualifiedName?.asString() == other.inner.qualifiedName?.asString()
  }

  override fun hashCode(): Int = inner.qualifiedName?.asString()?.hashCode()
    ?: inner.identityHashCode()
}

/**
 * A wrapper around the [KSFunctionDeclaration] interface that provides hashcode and equality
 * semantics.
 *
 * This class allows [KSFunctionDeclaration] instances to be used as keys in hash-based data
 * structures, such as [HashMap]s and [HashSet]s.
 */
class HashableFunction(private val inner: KSFunctionDeclaration) : KSFunctionDeclaration by inner {
  override fun equals(other: Any?): Boolean {
    if (this === other) return true
    if (other !is HashableFunction) return false
    return EqualsBuilder
      .with(inner.parameters.size, other.inner.parameters.size)
      .with(inner.typeParameters.size, other.inner.typeParameters.size)
      .with(inner.qualifiedName?.asString(), other.inner.qualifiedName?.asString())
      .with(inner.returnType?.element?.typeArguments?.size, other.inner.returnType?.element?.typeArguments?.size)
      .with(inner.returnType.logName(), other.inner.returnType.logName())
      .build()
  }

  override fun hashCode(): Int = HashCodeBuilder
    .append(inner.parameters.size)
    .append(inner.typeParameters.size)
    .append(inner.qualifiedName?.asString())
    .append(inner.returnType?.element?.typeArguments?.size)
    .append(inner.returnType.logName())
    .build()
}

/**
 * Wraps a [KSClassDeclaration] instance in a [HashableClass] instance.
 */
fun KSClassDeclaration.hashable() = HashableClass(this)

/**
 * Wraps a [KSFunctionDeclaration] instance in a [HashableFunction] instance.
 */
fun KSFunctionDeclaration.hashable() = HashableFunction(this)
