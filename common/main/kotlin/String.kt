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

import java.util.Locale

/**
 * Returns a copy of this string with the first character converted to lowercase using the
 * specified [Locale].
 *
 * @param locale The [Locale] to use for the lowercase conversion. If not specified, the default
 *   locale is used.
 * @return A new string with the first character in lowercase.
 */
fun String.lowercaseFirstChar(locale: Locale = Locale.getDefault()) =
  replaceFirstChar { it.lowercase(locale) }

/**
 * Returns a copy of this string with the first character converted to uppercase using the
 * specified [Locale].
 *
 * @param locale The [Locale] to use for the uppercase conversion. If not specified, the default
 *   locale is used.
 * @return A new string with the first character in uppercase.
 */
fun String.uppercaseFirstChar(locale: Locale = Locale.getDefault()) =
  replaceFirstChar { it.uppercase(locale) }

/**
 * Returns the current string if it is not null or empty, otherwise returns the result of
 * evaluating the [defaultValue] function.
 *
 * @param defaultValue A function that returns the default value to use if the current string is
 *   null or empty.
 */
fun String?.ifEmpty(defaultValue: () -> String): String =
  if (this.isNullOrEmpty()) defaultValue() else this

/**
 * Returns the current string if it is not null or blank, otherwise returns the result of
 * evaluating the [defaultValue] function.
 *
 * @param defaultValue A function that returns the default value to use if the current string is
 *   null or blank.
 */
fun String?.ifBlank(defaultValue: () -> String): String =
  if (this.isNullOrBlank()) defaultValue() else this
