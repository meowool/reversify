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
  "RemoveRedundantQualifierName",
  "ReplaceJavaStaticMethodWithKotlinAnalog",
)

package com.meowool.reflectify.common

/**
 * Calculates the hash code of the object.
 *
 * @receiver The object to calculate the hash code for.
 * @return The hash code of the object, or 0 if the object is null.
 */
@JvmName("hashCodeNullable")
inline fun Any?.hashCode(): Int = this?.hashCode() ?: 0

/**
 * Calculates the identity hash code of the object.
 *
 * @receiver The object to calculate the identity hash code for.
 * @return The identity hash code of the object, which is based on the object's memory address.
 */
inline fun Any.identityHashCode(): Int = System.identityHashCode(this)

/**
 * Calculates the identity hash code of the object.
 *
 * @receiver The object to calculate the identity hash code for.
 * @return The identity hash code of the object, which is based on the object's memory address.
 *   Or 0 if the object is null.
 */
@JvmName("identityHashCodeNullable")
inline fun Any?.identityHashCode(): Int = this?.identityHashCode() ?: 0

/**
 * A zero-overhead hash code builder for efficiently calculating hash codes.
 *
 * This builder is a value class designed to calculate hash codes with no additional memory
 * overhead. All of its functions are inlined, allowing it to completely replace manual hash code
 * calculation without any performance penalty.
 *
 * The hash code is calculated using a well-established algorithm that combines the hash codes of
 * the object's components using a constant multiplier (`31`) and addition. This ensures a good
 * distribution of hash values, which is crucial for the performance of hash-based data structures
 * like [HashMap] and [HashSet].
 *
 * Example usage:
 * ```
 * class Person(val name: String, val age: Int) {
 *   override fun hashCode(): Int = HashCodeBuilder
 *     .append(name)
 *     .append(age)
 *     .build()
 *
 *   ...
 * }
 * ```
 */
@JvmInline
value class HashCodeBuilder private constructor(internal val value: Int) {
  private inline val appendable get() = 31 * value

  /**
   * Appends the hash code of the given [value] to the current hash code.
   *
   * If the [value] is not `null`, this function delegates to the [Any.hashCode] function of the
   * object. If the [value] is `null`, this function uses `0` as the hash code.
   */
  inline fun append(value: Any?): HashCodeBuilder =
    HashCodeBuilder(appendable + value.hashCode())

  /**
   * Appends the hash code of the given [value] to the current hash code.
   *
   * This function delegates to the [java.lang.Boolean.hashCode] method to calculate the hash code
   * of the boolean value.
   */
  inline fun append(value: Boolean): HashCodeBuilder =
    HashCodeBuilder(appendable + java.lang.Boolean.hashCode(value))

  /**
   * Appends the hash code of the given [value] to the current hash code.
   *
   * This function delegates to the [java.lang.Byte.hashCode] method to calculate the hash code
   * of the byte value.
   */
  inline fun append(value: Byte): HashCodeBuilder =
    HashCodeBuilder(appendable + java.lang.Byte.hashCode(value))

  /**
   * Appends the hash code of the given [value] to the current hash code.
   *
   * This function delegates to the [java.lang.Short.hashCode] method to calculate the hash code
   * of the short value.
   */
  inline fun append(value: Short): HashCodeBuilder =
    HashCodeBuilder(appendable + java.lang.Short.hashCode(value))

  /**
   * Appends the hash code of the given [value] to the current hash code.
   *
   * This function delegates to the [java.lang.Integer.hashCode] method to calculate the hash code
   * of the integer value.
   */
  inline fun append(value: Int): HashCodeBuilder =
    HashCodeBuilder(appendable + java.lang.Integer.hashCode(value))

  /**
   * Appends the hash code of the given [value] to the current hash code.
   *
   * This function delegates to the [java.lang.Long.hashCode] method to calculate the hash code
   * of the long value.
   */
  inline fun append(value: Long): HashCodeBuilder =
    HashCodeBuilder(appendable + java.lang.Long.hashCode(value))

  /**
   * Appends the hash code of the given [value] to the current hash code.
   *
   * This function delegates to the [java.lang.Float.hashCode] method to calculate the hash code
   * of the float value.
   */
  inline fun append(value: Float): HashCodeBuilder =
    HashCodeBuilder(appendable + java.lang.Float.hashCode(value))

  /**
   * Appends the hash code of the given [value] to the current hash code.
   *
   * This function delegates to the [java.lang.Double.hashCode] method to calculate the hash code
   * of the double value.
   *
   * @param value The double value to append the hash code of.
   * @return A new [HashCodeBuilder] instance with the updated hash code.
   */
  inline fun append(value: Double): HashCodeBuilder =
    HashCodeBuilder(appendable + java.lang.Double.hashCode(value))

  /**
   * Appends the hash code of the given [value] to the current hash code.
   *
   * This function delegates to the [java.lang.Character.hashCode] method to calculate the hash code
   * of the character value.
   *
   * @param value The character value to append the hash code of.
   * @return A new [HashCodeBuilder] instance with the updated hash code.
   */
  inline fun append(value: Char): HashCodeBuilder =
    HashCodeBuilder(appendable + java.lang.Character.hashCode(value))

  /**
   * Appends the content hash code of the given [array] to the current hash code.
   *
   * This function delegates to the [Array.contentHashCode] method to calculate the hash code of
   * the array's contents. This method only considers the top-level array elements, and does not
   * recursively calculate the hash codes of any nested arrays.
   *
   * If you need to calculate the hash code of an array that contains nested arrays, you should use
   * the [appendDeep] function instead.
   */
  inline fun append(array: Array<*>): HashCodeBuilder =
    HashCodeBuilder(appendable + java.util.Arrays.hashCode(array))

  /**
   * Appends the content hash code of the given [array] to the current hash code.
   *
   * This function delegates to the [Array.contentHashCode] method to calculate the hash code of
   * the boolean array's contents.
   */
  inline fun append(array: BooleanArray): HashCodeBuilder =
    HashCodeBuilder(appendable + java.util.Arrays.hashCode(array))

  /**
   * Appends the content hash code of the given [array] to the current hash code.
   *
   * This function delegates to the [Array.contentHashCode] method to calculate the hash code of
   * the byte array's contents.
   */
  inline fun append(array: ByteArray): HashCodeBuilder =
    HashCodeBuilder(appendable + java.util.Arrays.hashCode(array))

  /**
   * Appends the hash code of the given [array] to the current hash code.
   *
   * This function delegates to the [Array.contentHashCode] method to calculate the hash code of
   * the short array's contents.
   */
  inline fun append(array: ShortArray): HashCodeBuilder =
    HashCodeBuilder(appendable + java.util.Arrays.hashCode(array))

  /**
   * Appends the hash code of the given [array] to the current hash code.
   *
   * This function delegates to the [Array.contentHashCode] method to calculate the hash code of
   * the integer array's contents.
   */
  inline fun append(array: IntArray): HashCodeBuilder =
    HashCodeBuilder(appendable + java.util.Arrays.hashCode(array))

  /**
   * Appends the hash code of the given [array] to the current hash code.
   *
   * This function delegates to the [Array.contentHashCode] method to calculate the hash code of
   * the long array's contents.
   */
  inline fun append(array: LongArray): HashCodeBuilder =
    HashCodeBuilder(appendable + java.util.Arrays.hashCode(array))

  /**
   * Appends the hash code of the given [array] to the current hash code.
   *
   * This function delegates to the [Array.contentHashCode] method to calculate the hash code of
   * the float array's contents.
   */
  inline fun append(array: FloatArray): HashCodeBuilder =
    HashCodeBuilder(appendable + java.util.Arrays.hashCode(array))

  /**
   * Appends the hash code of the given [array] to the current hash code.
   *
   * This function delegates to the [Array.contentHashCode] method to calculate the hash code of
   * the double array's contents.
   */
  inline fun append(array: DoubleArray): HashCodeBuilder =
    HashCodeBuilder(appendable + java.util.Arrays.hashCode(array))

  /**
   * Appends the hash code of the given [array] to the current hash code.
   *
   * This function delegates to the [Array.contentHashCode] method to calculate the hash code of
   * the character array's contents.
   */
  inline fun append(array: CharArray): HashCodeBuilder =
    HashCodeBuilder(appendable + java.util.Arrays.hashCode(array))

  /**
   * Appends the deep content hash code of the given [array] to the current hash code.
   *
   * This function delegates to the [java.util.Arrays.deepHashCode] method to calculate the
   * hash code of the array's contents, including the contents of any nested arrays.
   */
  inline fun appendDeep(array: Array<*>): HashCodeBuilder =
    HashCodeBuilder(appendable + java.util.Arrays.deepHashCode(array))

  /**
   * Appends the identity hash code of the given [value] to the current hash code.
   *
   * This function delegates to the [System.identityHashCode] method to calculate the identity
   * hash code of the object, which is based on the object's memory address. If the [value] is
   * `null`, this function uses `0` as the hash code.
   *
   * This is useful in cases where you want to include the identity of an object in the hash code,
   * rather than its state. For example, when using a map or set that uses identity equality
   * (e.g. `IdentityHashMap` or `IdentityHashSet`), you may want to use the identity hash code
   * instead of the regular hash code.
   */
  inline fun appendIdentity(value: Any?): HashCodeBuilder =
    HashCodeBuilder(appendable + value.identityHashCode())

  /**
   * Returns the final calculated hash code value.
   */
  inline fun build(): Int = value

  companion object {
    /**
     * Starts a new [HashCodeBuilder] chain with the initial hash code set to the hash code of the
     * given [value].
     *
     * If the [value] is not `null`, this function delegates to the [Any.hashCode] function to
     * calculate the initial hash code. If the [value] is `null`, this function uses `0` as
     * the initial hash code.
     */
    inline fun append(value: Any?): HashCodeBuilder =
      HashCodeBuilder(value.hashCode())

    /**
     * Starts a new [HashCodeBuilder] chain with the initial hash code set to the hash code of the
     * given boolean [value].
     *
     * This function delegates to the [java.lang.Boolean.hashCode] method to calculate the initial
     * hash code.
     */
    inline fun append(value: Boolean): HashCodeBuilder =
      HashCodeBuilder(java.lang.Boolean.hashCode(value))

    /**
     * Starts a new [HashCodeBuilder] chain with the initial hash code set to the hash code of the
     * given byte [value].
     *
     * This function delegates to the [java.lang.Byte.hashCode] method to calculate the initial
     * hash code.
     */
    inline fun append(value: Byte): HashCodeBuilder =
      HashCodeBuilder(java.lang.Byte.hashCode(value))

    /**
     * Starts a new [HashCodeBuilder] chain with the initial hash code set to the hash code of the
     * given short [value].
     *
     * This function delegates to the [java.lang.Short.hashCode] method to calculate the initial
     * hash code.
     */
    inline fun append(value: Short): HashCodeBuilder =
      HashCodeBuilder(java.lang.Short.hashCode(value))

    /**
     * Starts a new [HashCodeBuilder] chain with the initial hash code set to the hash code of the
     * given integer [value].
     *
     * This function delegates to the [java.lang.Integer.hashCode] method to calculate the initial
     * hash code.
     */
    inline fun append(value: Int): HashCodeBuilder =
      HashCodeBuilder(java.lang.Integer.hashCode(value))

    /**
     * Starts a new [HashCodeBuilder] chain with the initial hash code set to the hash code of the
     * given long [value].
     *
     * This function delegates to the [java.lang.Long.hashCode] method to calculate the initial
     * hash code.
     */
    inline fun append(value: Long): HashCodeBuilder =
      HashCodeBuilder(java.lang.Long.hashCode(value))

    /**
     * Starts a new [HashCodeBuilder] chain with the initial hash code set to the hash code of the
     * given float [value].
     *
     * This function delegates to the [java.lang.Float.hashCode] method to calculate the initial
     * hash code.
     */
    inline fun append(value: Float): HashCodeBuilder =
      HashCodeBuilder(java.lang.Float.hashCode(value))

    /**
     * Starts a new [HashCodeBuilder] chain with the initial hash code set to the hash code of the
     * given double [value].
     *
     * This function delegates to the [java.lang.Double.hashCode] method to calculate the initial
     * hash code.
     */
    inline fun append(value: Double): HashCodeBuilder =
      HashCodeBuilder(java.lang.Double.hashCode(value))

    /**
     * Starts a new [HashCodeBuilder] chain with the initial hash code set to the hash code of the
     * given character [value].
     *
     * This function delegates to the [java.lang.Character.hashCode] method to calculate the initial
     * hash code.
     */
    inline fun append(value: Char): HashCodeBuilder =
      HashCodeBuilder(java.lang.Character.hashCode(value))

    /**
     * Starts a new [HashCodeBuilder] chain with the initial hash code set to the content hash code
     * of the given array [array].
     *
     * This function delegates to the [Array.contentHashCode] method to calculate the initial hash
     * code of the array's contents. This method only considers the top-level array elements, and
     * does not recursively calculate the hash codes of any nested arrays.
     *
     * If you need to calculate the hash code of an array that contains nested arrays, you should
     * use the [appendDeep] function instead.
     */
    inline fun append(array: Array<*>): HashCodeBuilder =
      HashCodeBuilder(array.contentHashCode())

    /**
     * Starts a new [HashCodeBuilder] chain with the initial hash code set to the content hash code
     * of the given boolean array [array].
     *
     * This function delegates to the [Array.contentHashCode] method to calculate the initial hash
     * code of the boolean array's contents.
     */
    inline fun append(array: BooleanArray): HashCodeBuilder =
      HashCodeBuilder(array.contentHashCode())

    /**
     * Starts a new [HashCodeBuilder] chain with the initial hash code set to the content hash code
     * of the given byte array [array].
     *
     * This function delegates to the [Array.contentHashCode] method to calculate the initial hash
     * code of the byte array's contents.
     */
    inline fun append(array: ByteArray): HashCodeBuilder =
      HashCodeBuilder(array.contentHashCode())

    /**
     * Starts a new [HashCodeBuilder] chain with the initial hash code set to the content hash code
     * of the given short array [array].
     *
     * This function delegates to the [Array.contentHashCode] method to calculate the initial hash
     * code of the short array's contents.
     */
    inline fun append(array: ShortArray): HashCodeBuilder =
      HashCodeBuilder(array.contentHashCode())

    /**
     * Starts a new [HashCodeBuilder] chain with the initial hash code set to the content hash code
     * of the given integer array [array].
     *
     * This function delegates to the [Array.contentHashCode] method to calculate the initial hash
     * code of the integer array's contents.
     */
    inline fun append(array: IntArray): HashCodeBuilder =
      HashCodeBuilder(array.contentHashCode())

    /**
     * Starts a new [HashCodeBuilder] chain with the initial hash code set to the content hash code
     * of the given long array [array].
     *
     * This function delegates to the [Array.contentHashCode] method to calculate the initial hash
     * code of the long array's contents.
     */
    inline fun append(array: LongArray): HashCodeBuilder =
      HashCodeBuilder(array.contentHashCode())

    /**
     * Starts a new [HashCodeBuilder] chain with the initial hash code set to the content hash code
     * of the given float array [array].
     *
     * This function delegates to the [Array.contentHashCode] method to calculate the initial hash
     * code of the float array's contents.
     */
    inline fun append(array: FloatArray): HashCodeBuilder =
      HashCodeBuilder(array.contentHashCode())

    /**
     * Starts a new [HashCodeBuilder] chain with the initial hash code set to the content hash code
     * of the given double array [array].
     *
     * This function delegates to the [Array.contentHashCode] method to calculate the initial hash
     * code of the double array's contents.
     */
    inline fun append(array: DoubleArray): HashCodeBuilder =
      HashCodeBuilder(array.contentHashCode())

    /**
     * Starts a new [HashCodeBuilder] chain with the initial hash code set to the content hash code
     * of the given character array [array].
     *
     * This function delegates to the [Array.contentHashCode] method to calculate the initial hash
     * code of the character array's contents.
     */
    inline fun append(array: CharArray): HashCodeBuilder =
      HashCodeBuilder(array.contentHashCode())

    /**
     * Starts a new [HashCodeBuilder] chain with the initial hash code set to the deep content
     * hash code of the given array [array].
     *
     * This function delegates to the [java.util.Arrays.deepHashCode] method to calculate the
     * initial hash code of the array's contents, including the contents of any nested arrays.
     */
    inline fun appendDeep(array: Array<*>): HashCodeBuilder =
      HashCodeBuilder(java.util.Arrays.deepHashCode(array))

    /**
     * Starts a new [HashCodeBuilder] chain with the initial hash code set to the identity hash
     * code of the given [value].
     *
     * This function delegates to the [System.identityHashCode] method to calculate the initial
     * hash code, which is based on the object's memory address. If the [value] is `null`, the
     * initial hash code will be set to zero.
     *
     * This is useful in cases where you want to include the identity of an object in the hash code,
     * rather than its state. For example, when using a map or set that uses identity equality
     * (e.g. `IdentityHashMap` or `IdentityHashSet`), you may want to use the identity hash code
     * instead of the regular hash code.
     */
    inline fun appendIdentity(value: Any?): HashCodeBuilder =
      HashCodeBuilder(value.identityHashCode())
  }
}
