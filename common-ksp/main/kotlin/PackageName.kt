package ai.sider.common.ksp

import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.MemberName

/**
 * A class that represents a Kotlin package name and provides utility functions for working with it.
 *
 * @property packageName The fully qualified package name.
 */
class PackageName(private val packageName: String) {
  /**
   * Creates a new [PackageName] instance by appending the given name to the current packageName.
   *
   * @param name The name to be appended to the packageName.
   * @return A new [PackageName] instance with the updated package name.
   */
  fun plus(name: String) = PackageName("$packageName.$name")

  /**
   * Creates a new [ClassName] instance using the current packageName and the given class name.
   *
   * @param name The simple name of the class.
   * @return A new [ClassName] instance representing the fully qualified class name.
   */
  fun classFor(name: String) = ClassName(this.packageName, name)

  /**
   * Creates a new [MemberName] instance using the current packageName and the given member name.
   *
   * If an enclosingClassName is provided, the [MemberName] will be associated with the
   * corresponding [ClassName]. That is, the [MemberName] will represent a member of the
   * enclosing class.
   *
   * @param name The simple name of the member.
   * @param enclosingClassName The name of the enclosing class, if any.
   * @return A new [MemberName] instance representing the fully qualified member name.
   */
  fun memberFor(name: String, enclosingClassName: String? = null) = when (enclosingClassName) {
    null -> MemberName(
      packageName = this.packageName,
      simpleName = name,
    )
    else -> MemberName(
      enclosingClassName = ClassName(packageName, enclosingClassName),
      simpleName = name,
    )
  }
}
