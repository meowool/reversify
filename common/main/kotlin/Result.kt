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

package com.meowool.reflectify.common

/**
 * Applies the provided [transform] function to the exception of this [Result], and returns a new
 * [Result] with the transformed exception.
 *
 * If this [Result] is successful, the original [Result] is returned.
 *
 * Example usage:
 * ```
 * val result: Result<Int> = Result.failure(IllegalArgumentException("Invalid input"))
 * val transformedResult = result.mapFailure { IOException("Failed to process input", it) }
 * // transformedResult is Result.failure(IOException("Failed to process input", IllegalArgumentException("Invalid input")))
 * ```
 *
 * @param transform A function that takes the exception and returns a new exception.
 * @return A new [Result] with the transformed exception, or the original [Result] if it's successful.
 */
inline fun <R, T : R> Result<T>.mapFailure(transform: (exception: Throwable) -> Throwable): Result<R> {
  return when (val exception = exceptionOrNull()) {
    null -> this
    else -> Result.failure(transform(exception))
  }
}

/**
 * Applies the provided [transform] function to the successful value of this [Result], and returns
 * a new [Result] with the transformed value.
 *
 * If this [Result] is a failure, the original [Result] is returned.
 *
 * Example usage:
 * ```
 * val result: Result<Int> = Result.success(42)
 * val transformedResult = result.flatMap { value -> Result.success(value * 2) }
 * // transformedResult is Result.success(84)
 * ```
 *
 * @param transform A function that takes the successful value and returns a new [Result].
 * @return A new [Result] with the transformed value, or the original [Result] if it's a failure.
 */
inline fun <R, T> Result<T>.flatMap(transform: (T) -> Result<R>): Result<R> {
  return map(transform).fold(
    onSuccess = { it },
    onFailure = { Result.failure(it) }
  )
}

/**
 * Applies the provided [transform] function to the successful value of this [Result], catching any
 * exceptions that may be thrown, and returns a new [Result] with the transformed value.
 *
 * If this [Result] is a failure, the original [Result] is returned.
 *
 * Example usage:
 * ```
 * val result: Result<Int> = Result.success(42)
 * val transformedResult = result.flatMapCatching { value -> Result.success(value.toString().toInt()) }
 * // transformedResult is Result.success(42)
 *
 * val failedResult: Result<Int> = Result.success("not a number")
 * val failedTransformedResult = failedResult.flatMapCatching { value -> Result.success(value.toString().toInt()) }
 * // failedTransformedResult is Result.failure(NumberFormatException)
 * ```
 *
 * @param transform A function that takes the successful value and returns a new [Result],
 *   potentially throwing an exception.
 *
 * @return A new [Result] with the transformed value, or the original [Result] if it's a failure
 *   or an exception was thrown during the transformation.
 */
inline fun <R, T> Result<T>.flatMapCatching(transform: (T) -> Result<R>): Result<R> {
  return mapCatching(transform).fold(
    onSuccess = { it },
    onFailure = { Result.failure(it) }
  )
}

/**
 * Executes the provided [block] function when this [Result] is either successful or a failure, and
 * returns the original [Result].
 *
 * Example usage:
 * ```
 * val result: Result<Int> = Result.success(42)
 * val finalResult = result.finally { exception -> println("Result processing finished: $exception") }
 * // Prints "Result processing finished: null"
 *
 * val failedResult: Result<Int> = Result.failure(IllegalArgumentException("Invalid input"))
 * val finalFailedResult = failedResult.finally { exception -> println("Result processing finished: $exception") }
 * // Prints "Result processing finished: java.lang.IllegalArgumentException: Invalid input"
 * ```
 *
 * @param block A function that takes the exception (or `null` if the [Result] is successful) and
 *   performs some cleanup or side-effect operation.
 * @return The original [Result].
 */
inline fun <T> Result<T>.finally(block: (exception: Throwable?) -> Unit): Result<T> {
  onSuccess { block(null) }
  onFailure(block)
  return this
}
