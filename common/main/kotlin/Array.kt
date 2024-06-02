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
  "INVISIBLE_REFERENCE",
  "INVISIBLE_MEMBER",
  "UNCHECKED_CAST",
  "NOTHING_TO_INLINE",
  "NON_PUBLIC_CALL_FROM_PUBLIC_INLINE",
  "ReplaceJavaStaticMethodWithKotlinAnalog",
)

package com.meowool.reflectify.common

import java.util.Arrays
import kotlin.internal.PureReifiable
import java.lang.reflect.Array.newInstance as newArray

// region Internal Implementation

internal fun <T> Array<out T?>.fastFilterNotNull(componentType: Class<*>): Array<T> {
  // Create a new array with the same size as the original array
  val destination = newArray(componentType, size) as Array<T>
  var destinationIndex = 0

  // Iterate through the original array and copy non-null elements to the new array
  for (index in indices) {
    val element = this[index]
    if (element != null) {
      destination[destinationIndex++] = element
    }
  }

  // If the new array has the same size as the original, return it as-is.
  // Otherwise, return a copy with the correct size.
  return if (destinationIndex == size) destination else Arrays.copyOf(destination, destinationIndex)
}

internal fun <T> Array<out T>.fastDrop(n: Int, componentType: Class<*>): Array<out T> {
  require(n >= 0) { "n must be non-negative, got $n" }
  if (n >= this.size) return this
  val resultSize = size - n
  if (resultSize <= 0) return newArray(componentType, 0) as Array<out T>
  val destination = newArray(componentType, resultSize) as Array<T>
  System.arraycopy(this, n, destination, 0, resultSize)
  return destination
}

// endregion

/**
 * Creates a new array containing only the non-null elements from the provided [Array].
 *
 * This function is optimized for performance, operating directly on the input array without
 * the need to create a new collection. It's more efficient than using the Kotlin's standard
 * [Array.filterNotNull] extension function.
 *
 * @receiver the input [Array] of nullable elements to filter.
 * @return a new [Array] containing only the non-null elements from the input [Array].
 */
inline fun <@PureReifiable reified T> Array<out T?>.fastFilterNotNull(): Array<T> =
  fastFilterNotNull(componentType = T::class.java)

/**
 * Creates a new array containing only the non-null elements from the provided [elements].
 *
 * This is an alias for [Array.filterNotNull].
 */
inline fun <@PureReifiable reified T> arrayOfNotNull(vararg elements: T?): Array<T> =
  elements.fastFilterNotNull()

/**
 * Returns a new array containing all elements from the original array except the first [n]
 * elements.
 *
 * This function is optimized for performance, operating directly on the input array without
 * the need to create a new collection. It's more efficient than using the Kotlin's standard
 * [Array.drop] extension function.
 *
 * @receiver the original array
 * @param n the number of elements to drop from the beginning of the array
 * @return a new array containing the remaining elements after dropping the first [n] elements
 */
inline fun <T> Array<out T>.fastDrop(n: Int): Array<out T> =
  fastDrop(n, componentType = this::class.java)
