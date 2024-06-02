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

package com.meowool.reflectify.common.android

import android.util.Log

/**
 * Determines whether logging is enabled based on the build configuration.
 */
val loggable: Boolean inline get() = BuildConfig.DEBUG || BuildConfig.PRE_RELEASE

/**
 * Logs a debug message with the specified tag.
 *
 * The message is only logged if the app is in debug mode.
 *
 * @param tag The tag to associate with the log message.
 * @param message A lambda that returns the message to be logged.
 */
inline fun debug(
  tag: String,
  message: () -> String,
) {
  if (!loggable) return
  Log.d(tag, message())
}

/**
 * Logs a debug message with the tag derived from the calling class.
 *
 * The message is only logged if the app is in debug mode.
 *
 * @param message A lambda that returns the message to be logged.
 */
inline fun <reified T> T.debug(message: () -> String) =
  debug(T::class.java.simpleName, message)

/**
 * Logs a debug message with the specified tag and the associated exception.
 * This will also print the stack trace of the exception.
 *
 * The message is only logged if the app is in debug mode.
 *
 * @param tag The tag to associate with the log message.
 * @param throwable The exception to be logged.
 * @param message A lambda that returns the message to be logged.
 */
inline fun debug(
  tag: String,
  throwable: Throwable,
  message: () -> String,
) {
  if (loggable) {
    Log.d(tag, message(), throwable)
    throwable.printStackTrace()
  }
}

/**
 * Logs a debug message with the tag derived from the calling class and the associated exception.
 *
 * The message is only logged if the app is in debug mode.
 *
 * @param throwable The exception to be logged.
 * @param message A lambda that returns the message to be logged.
 */
inline fun <reified T> T.debug(throwable: Throwable, message: () -> String) =
  debug(T::class.java.simpleName, throwable, message)

/**
 * Logs the failure of the [Result] using the provided [tag] and [message].
 *
 * If this [Result] instance represents a failure, it will log the exception with the given
 * [tag] and [message]. This is a convenient helper function to handle failure cases in a
 * concise manner.
 *
 * @param tag The tag to use for logging the failure.
 * @param message A lambda function that provides the message to be logged.
 *
 * @see Result.onFailure
 */
inline fun <R> Result<R>.debugOnFailure(tag: String, message: () -> String) =
  onFailure { debug(tag, it, message) }

/**
 * Logs the failure of the [Result] using the provided [message].
 *
 * If this [Result] instance represents a failure, it will log the exception with the given
 * [message]. This function uses the [T] type as the logging tag.
 *
 * @param message A lambda function that provides the message to be logged.
 */
context(T)
inline fun <reified T, R> Result<R>.debugOnFailure(message: () -> String) =
  onFailure { debug(it, message) }
