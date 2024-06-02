/*
 * Copyright (C) 2024 Meowool <https://github.com/meowool/graphs/contributors>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * This file is part of the Reversify project <https://github.com/meowool/reversify>.
 */

@file:Suppress(
  "NOTHING_TO_INLINE",
  "NON_PUBLIC_CALL_FROM_PUBLIC_INLINE",
  "ReplaceJavaStaticMethodWithKotlinAnalog",
)

package com.meowool.reflectify.common

import java.util.Arrays

/**
 * A zero-overhead equals builder for efficiently calculating object equality.
 *
 * This builder is a value class designed to compare objects for equality with no additional memory
 * overhead. All of its functions are inlined, allowing it to completely replace manual equality
 * comparison without any performance penalty.
 *
 * The equality comparison is performed using a series of checks that evaluate the equality of
 * the object's components. If any of the components are not equal, the comparison short-circuits
 * and returns `false`. If all components are equal, the comparison returns `true`.
 *
 * Example usage:
 * ```
 * class Person(val name: String, val age: Int) {
 *   override fun equals(other: Any?): Boolean {
 *     if (this === other) return true
 *     if (other !is Method<*, *>) return false // Or use `EqualsBuilder.withClass(this, other)`
 *     return EqualsBuilder
 *       .with(name, it.name)
 *       .with(age, it.age)
 *       .build()
 *   }
 *
 *   ...
 * }
 * ```
 */
@JvmInline
value class EqualsBuilder private constructor(internal val value: Boolean) {
  /**
   * Checks the provided [checks] lambda to determine if the chain can continue.
   *
   * If the [checks] lambda returns `true`, the chain can continue, otherwise the chain will be
   * short-circuited and return `false`.
   */
  inline fun with(checks: () -> Boolean): EqualsBuilder {
    // If the value is no longer equal, we don't need to continue :）
    if (!value) return this
    return EqualsBuilder(checks())
  }

  /**
   * Compares the reference equality of the two given [a] and [b] objects.
   *
   * If both [a] and [b] are non-null and the same object reference, the chain can continue,
   * otherwise the chain will be short-circuited and return `false`.
   */
  inline fun withReference(a: Any?, b: Any?): EqualsBuilder = this.with { a === b }

  /**
   * Compares the class equality of the two given [a] and [b] objects.
   *
   * If both [a] and [b] are non-null and have the same class, the chain can continue, otherwise
   * the chain will be short-circuited and return `false`.
   *
   * @see Any.javaClass
   */
  inline fun withClass(a: Any?, b: Any?): EqualsBuilder = this.with { a?.javaClass == b?.javaClass }

  /**
   * Checks if the given [instance] object is an instance of the specified generic type [T].
   *
   * If the [instance] object is an instance of type [T], the chain can continue, otherwise the
   * chain will be short-circuited and return `false`.
   *
   * A stricter check is [withClass], which checks the exact type of the object.
   */
  inline fun <reified T> withInstance(instance: Any?): EqualsBuilder = this.with { instance is T }

  /**
   * Compares the equality of the two given [a] and [b] objects.
   *
   * If both [a] and [b] are non-null and are equal according to the [Any.equals] method, the chain
   * can continue, otherwise the chain will be short-circuited and return `false`.
   */
  inline fun with(a: Any?, b: Any?): EqualsBuilder = this.with { a == b }

  /**
   * Compares the equality of the two given boolean [a] and [b] values.
   *
   * If both [a] and [b] are equal, the chain can continue, otherwise the chain will be
   * short-circuited and return `false`.
   */
  inline fun with(a: Boolean, b: Boolean): EqualsBuilder = this.with { a == b }

  /**
   * Compares the equality of the two given byte [a] and [b] values.
   *
   * If both [a] and [b] are equal, the chain can continue, otherwise the chain will be
   * short-circuited and return `false`.
   */
  inline fun with(a: Byte, b: Byte): EqualsBuilder = this.with { a == b }

  /**
   * Compares the equality of the two given char [a] and [b] values.
   *
   * If both [a] and [b] are equal, the chain can continue, otherwise the chain will be
   * short-circuited and return `false`.
   */
  inline fun with(a: Char, b: Char): EqualsBuilder = this.with { a == b }

  /**
   * Compares the equality of the two given short [a] and [b] values.
   *
   * If both [a] and [b] are equal, the chain can continue, otherwise the chain will be
   * short-circuited and return `false`.
   */
  inline fun with(a: Short, b: Short): EqualsBuilder = this.with { a == b }

  /**
   * Compares the equality of the two given int [a] and [b] values.
   *
   * If both [a] and [b] are equal, the chain can continue, otherwise the chain will be
   * short-circuited and return `false`.
   */
  inline fun with(a: Int, b: Int): EqualsBuilder = this.with { a == b }

  /**
   * Compares the equality of the two given long [a] and [b] values.
   *
   * If both [a] and [b] are equal, the chain can continue, otherwise the chain will be
   * short-circuited and return `false`.
   */
  inline fun with(a: Long, b: Long): EqualsBuilder = this.with { a == b }

  /**
   * Compares the equality of the two given float [a] and [b] values.
   *
   * If both [a] and [b] are equal, the chain can continue, otherwise the chain will be
   * short-circuited and return `false`.
   */
  inline fun with(a: Float, b: Float): EqualsBuilder = this.with { a == b }

  /**
   * Compares the equality of the two given double [a] and [b] values.
   *
   * If both [a] and [b] are equal, the chain can continue, otherwise the chain will be
   * short-circuited and return `false`.
   */
  inline fun with(a: Double, b: Double): EqualsBuilder = this.with { a == b }

  /**
   * Compares the content equality of the two given [a] and [b] arrays.
   *
   * If both [a] and [b] are non-null and have the same content according to the
   * [Array.contentEquals] method, the chain can continue, otherwise the chain will be
   * short-circuited and return `false`.
   */
  inline fun with(a: Array<*>?, b: Array<*>?): EqualsBuilder =
    this.with { a.contentEquals(b) }

  /**
   * Compares the content equality of the two given [a] and [b] boolean arrays.
   *
   * If both [a] and [b] are non-null and have the same content according to the
   * [BooleanArray.contentEquals] method, the chain can continue, otherwise the chain will be
   * short-circuited and return `false`.
   */
  inline fun with(a: BooleanArray, b: BooleanArray): EqualsBuilder =
    this.with { a.contentEquals(b) }

  /**
   * Compares the content equality of the two given [a] and [b] byte arrays.
   *
   * If both [a] and [b] are non-null and have the same content according to the
   * [ByteArray.contentEquals] method, the chain can continue, otherwise the chain will be
   * short-circuited and return `false`.
   */
  inline fun with(a: ByteArray, b: ByteArray): EqualsBuilder =
    this.with { a.contentEquals(b) }

  /**
   * Compares the content equality of the two given [a] and [b] char arrays.
   *
   * If both [a] and [b] are non-null and have the same content according to the
   * [CharArray.contentEquals] method, the chain can continue, otherwise the chain will be
   * short-circuited and return `false`.
   */
  inline fun with(a: CharArray, b: CharArray): EqualsBuilder =
    this.with { a.contentEquals(b) }

  /**
   * Compares the content equality of the two given [a] and [b] short arrays.
   *
   * If both [a] and [b] are non-null and have the same content according to the
   * [ShortArray.contentEquals] method, the chain can continue, otherwise the chain will be
   * short-circuited and return `false`.
   */
  inline fun with(a: ShortArray, b: ShortArray): EqualsBuilder =
    this.with { a.contentEquals(b) }

  /**
   * Compares the content equality of the two given [a] and [b] int arrays.
   *
   * If both [a] and [b] are non-null and have the same content according to the
   * [IntArray.contentEquals] method, the chain can continue, otherwise the chain will be
   * short-circuited and return `false`.
   */
  inline fun with(a: IntArray, b: IntArray): EqualsBuilder =
    this.with { a.contentEquals(b) }

  /**
   * Compares the content equality of the two given [a] and [b] long arrays.
   *
   * If both [a] and [b] are non-null and have the same content according to the
   * [LongArray.contentEquals] method, the chain can continue, otherwise the chain will be
   * short-circuited and return `false`.
   */
  inline fun with(a: LongArray, b: LongArray): EqualsBuilder =
    this.with { a.contentEquals(b) }

  /**
   * Compares the content equality of the two given [a] and [b] float arrays.
   *
   * If both [a] and [b] are non-null and have the same content according to the
   * [FloatArray.contentEquals] method, the chain can continue, otherwise the chain will be
   * short-circuited and return `false`.
   */
  inline fun with(a: FloatArray, b: FloatArray): EqualsBuilder =
    this.with { a.contentEquals(b) }

  /**
   * Compares the content equality of the two given [a] and [b] double arrays.
   *
   * If both [a] and [b] are non-null and have the same content according to the
   * [DoubleArray.contentEquals] method, the chain can continue, otherwise the chain will be
   * short-circuited and return `false`.
   */
  inline fun with(a: DoubleArray, b: DoubleArray): EqualsBuilder =
    this.with { a.contentEquals(b) }

  /**
   * Compares the contents of two nullable arrays using deep equality.
   *
   * If both [a] and [b] are non-null and have the same content according to the
   * [Arrays.deepEquals] method, the chain can continue, otherwise the chain will be
   * short-circuited and return `false`.
   */
  inline fun withDeep(a: Array<*>?, b: Array<*>?): EqualsBuilder =
    this.with { Arrays.deepEquals(a, b) }

  /**
   * Returns the final calculated equality result.
   */
  inline fun build(): Boolean = value

  companion object {
    /**
     * Starts a new [EqualsBuilder] chain and compares the reference equality of the two given
     * [a] and [b] objects.
     *
     * If both [a] and [b] are non-null and the same object reference, the chain can continue,
     * otherwise the chain will be short-circuited and return `false`.
     *
     * @see EqualsBuilder.withReference
     */
    inline fun withReference(a: Any?, b: Any?): EqualsBuilder = EqualsBuilder(a === b)

    /**
     * Starts a new [EqualsBuilder] chain and compares the class equality of the two given [a]
     * and [b] objects.
     *
     * If both [a] and [b] are non-null and have the same class, the chain can continue, otherwise
     * the chain will be short-circuited and return `false`.
     *
     * @see EqualsBuilder.withClass
     */
    inline fun withClass(a: Any?, b: Any?): EqualsBuilder = EqualsBuilder(a?.javaClass == b?.javaClass)

    /**
     * Starts a new [EqualsBuilder] chain and checks if the given [instance] object is an instance
     * of the specified generic type [T].
     *
     * If the [instance] object is an instance of type [T], the chain can continue, otherwise the
     * chain will be short-circuited and return `false`.
     *
     * A stricter check is [withClass], which checks the exact type of the object.
     *
     * @see EqualsBuilder.withInstance
     */
    inline fun <reified T> withInstance(instance: Any?): EqualsBuilder = EqualsBuilder(instance is T)

    /**
     * Starts a new [EqualsBuilder] chain and compares the equality of the two given [a] and [b]
     * objects.
     *
     * If both [a] and [b] are non-null and are equal according to the [Any.equals] method, the
     * chain can continue, otherwise the chain will be short-circuited and return `false`.
     */
    inline fun with(a: Any?, b: Any?): EqualsBuilder = EqualsBuilder(a == b)

    /**
     * Starts a new [EqualsBuilder] chain and compares the equality of the two given boolean [a]
     * and [b] values.
     *
     * If both [a] and [b] are equal, the chain can continue, otherwise the chain will be
     * short-circuited and return `false`.
     */
    inline fun with(a: Boolean, b: Boolean): EqualsBuilder = EqualsBuilder(a == b)

    /**
     * Starts a new [EqualsBuilder] chain and compares the equality of the two given byte [a] and
     * [b] values.
     *
     * If both [a] and [b] are equal, the chain can continue, otherwise the chain will be
     * short-circuited and return `false`.
     */
    inline fun with(a: Byte, b: Byte): EqualsBuilder = EqualsBuilder(a == b)

    /**
     * Starts a new [EqualsBuilder] chain and compares the equality of the two given char [a] and
     * [b] values.
     *
     * If both [a] and [b] are equal, the chain can continue, otherwise the chain will be
     * short-circuited and return `false`.
     */
    inline fun with(a: Char, b: Char): EqualsBuilder = EqualsBuilder(a == b)

    /**
     * Starts a new [EqualsBuilder] chain and compares the equality of the two given short [a] and
     * [b] values.
     *
     * If both [a] and [b] are equal, the chain can continue, otherwise the chain will be
     * short-circuited and return `false`.
     */
    inline fun with(a: Short, b: Short): EqualsBuilder = EqualsBuilder(a == b)

    /**
     * Starts a new [EqualsBuilder] chain and compares the equality of the two given int [a] and
     * [b] values.
     *
     * If both [a] and [b] are equal, the chain can continue, otherwise the chain will be
     * short-circuited and return `false`.
     */
    inline fun with(a: Int, b: Int): EqualsBuilder = EqualsBuilder(a == b)

    /**
     * Starts a new [EqualsBuilder] chain and compares the equality of the two given long [a] and
     * [b] values.
     *
     * If both [a] and [b] are equal, the chain can continue, otherwise the chain will be
     * short-circuited and return `false`.
     */
    inline fun with(a: Long, b: Long): EqualsBuilder = EqualsBuilder(a == b)

    /**
     * Starts a new [EqualsBuilder] chain and compares the equality of the two given float [a] and
     * [b] values.
     *
     * If both [a] and [b] are equal, the chain can continue, otherwise the chain will be
     * short-circuited and return `false`.
     */
    inline fun with(a: Float, b: Float): EqualsBuilder = EqualsBuilder(a == b)

    /**
     * Starts a new [EqualsBuilder] chain and compares the equality of the two given double [a] and
     * [b] values.
     *
     * If both [a] and [b] are equal, the chain can continue, otherwise the chain will be
     * short-circuited and return `false`.
     */
    inline fun with(a: Double, b: Double): EqualsBuilder = EqualsBuilder(a == b)

    /**
     * Starts a new [EqualsBuilder] chain and compares the content equality of the two given
     * [a] and [b] arrays.
     *
     * If both [a] and [b] are non-null and have the same content according to the
     * [Array.contentEquals] method, the chain can continue, otherwise the chain will be
     * short-circuited and return `false`.
     */
    inline fun with(a: Array<*>?, b: Array<*>?): EqualsBuilder = EqualsBuilder(a.contentEquals(b))

    /**
     * Starts a new [EqualsBuilder] chain and compares the content equality of the two given
     * [a] and [b] boolean arrays.
     *
     * If both [a] and [b] are non-null and have the same content according to the
     * [BooleanArray.contentEquals] method, the chain can continue, otherwise the chain will be
     * short-circuited and return `false`.
     */
    inline fun with(a: BooleanArray, b: BooleanArray): EqualsBuilder = EqualsBuilder(a.contentEquals(b))

    /**
     * Starts a new [EqualsBuilder] chain and compares the content equality of the two given
     * [a] and [b] byte arrays.
     *
     * If both [a] and [b] are non-null and have the same content according to the
     * [ByteArray.contentEquals] method, the chain can continue, otherwise the chain will be
     * short-circuited and return `false`.
     */
    inline fun with(a: ByteArray, b: ByteArray): EqualsBuilder = EqualsBuilder(a.contentEquals(b))

    /**
     * Starts a new [EqualsBuilder] chain and compares the content equality of the two given
     * [a] and [b] char arrays.
     *
     * If both [a] and [b] are non-null and have the same content according to the
     * [CharArray.contentEquals] method, the chain can continue, otherwise the chain will be
     * short-circuited and return `false`.
     */
    inline fun with(a: CharArray, b: CharArray): EqualsBuilder = EqualsBuilder(a.contentEquals(b))

    /**
     * Starts a new [EqualsBuilder] chain and compares the content equality of the two given
     * [a] and [b] short arrays.
     *
     * If both [a] and [b] are non-null and have the same content according to the
     * [ShortArray.contentEquals] method, the chain can continue, otherwise the chain will be
     * short-circuited and return `false`.
     */
    inline fun with(a: ShortArray, b: ShortArray): EqualsBuilder = EqualsBuilder(a.contentEquals(b))

    /**
     * Starts a new [EqualsBuilder] chain and compares the content equality of the two given
     * [a] and [b] int arrays.
     *
     * If both [a] and [b] are non-null and have the same content according to the
     * [IntArray.contentEquals] method, the chain can continue, otherwise the chain will be
     * short-circuited and return `false`.
     */
    inline fun with(a: IntArray, b: IntArray): EqualsBuilder = EqualsBuilder(a.contentEquals(b))

    /**
     * Starts a new [EqualsBuilder] chain and compares the content equality of the two given
     * [a] and [b] long arrays.
     *
     * If both [a] and [b] are non-null and have the same content according to the
     * [LongArray.contentEquals] method, the chain can continue, otherwise the chain will be
     * short-circuited and return `false`.
     */
    inline fun with(a: LongArray, b: LongArray): EqualsBuilder = EqualsBuilder(a.contentEquals(b))

    /**
     * Starts a new [EqualsBuilder] chain and compares the content equality of the two given
     * [a] and [b] float arrays.
     *
     * If both [a] and [b] are non-null and have the same content according to the
     * [FloatArray.contentEquals] method, the chain can continue, otherwise the chain will be
     * short-circuited and return `false`.
     */
    inline fun with(a: FloatArray, b: FloatArray): EqualsBuilder = EqualsBuilder(a.contentEquals(b))

    /**
     * Starts a new [EqualsBuilder] chain and compares the content equality of the two given
     * [a] and [b] double arrays.
     *
     * If both [a] and [b] are non-null and have the same content according to the
     * [DoubleArray.contentEquals] method, the chain can continue, otherwise the chain will be
     * short-circuited and return `false`.
     */
    inline fun with(a: DoubleArray, b: DoubleArray): EqualsBuilder = EqualsBuilder(a.contentEquals(b))

    /**
     * Starts a new [EqualsBuilder] chain and compares the contents of two nullable arrays
     * using deep equality.
     *
     * If both [a] and [b] are non-null and have the same content according to the
     * [Arrays.deepEquals] method, the chain can continue, otherwise the chain will be
     * short-circuited and return `false`.
     */
    inline fun withDeep(a: Array<*>?, b: Array<*>?): EqualsBuilder = EqualsBuilder(Arrays.deepEquals(a, b))
  }
}
