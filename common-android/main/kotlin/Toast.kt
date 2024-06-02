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

import android.content.Context
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat

/**
 * Displays a toast message with the given resource ID and duration.
 *
 * @param resource The resource ID of the string to be displayed.
 * @param duration The duration of the toast message (either `[Toast.LENGTH_SHORT]` or `[Toast.LENGTH_LONG]`).
 * @param block An optional block of code to be executed on the toast instance.
 */
inline fun Context.toast(
  @StringRes
  resource: Int,
  duration: Int,
  block: Toast.() -> Unit = {},
): Toast = toast(
  text = ContextCompat.getString(applicationContext, resource),
  duration = duration,
  block = block,
)

/**
 * Displays a toast message with the given text and duration.
 *
 * @param text The text to be displayed in the toast message.
 * @param duration The duration of the toast message (either `[Toast.LENGTH_SHORT]` or `[Toast.LENGTH_LONG]`).
 * @param block An optional block of code to be executed on the toast instance.
 */
inline fun Context.toast(
  text: String,
  duration: Int,
  block: Toast.() -> Unit = {},
): Toast = Toast.makeText(applicationContext, text, duration).also {
  block(it)
  it.show()
}

/**
 * Displays a short toast message with the given resource ID.
 *
 * @param resource The resource ID of the string to be displayed.
 * @param block An optional block of code to be executed on the toast instance.
 */
inline fun Context.toastShort(
  @StringRes resource: Int,
  block: Toast.() -> Unit = {},
): Toast = toast(resource, Toast.LENGTH_SHORT, block)

/**
 * Displays a short toast message with the given text.
 *
 * @param text The text to be displayed in the toast message.
 * @param block An optional block of code to be executed on the toast instance.
 */
inline fun Context.toastShort(
  text: String,
  block: Toast.() -> Unit = {},
): Toast = toast(text, Toast.LENGTH_SHORT, block)

/**
 * Displays a long toast message with the given resource ID.
 *
 * @param resource The resource ID of the string to be displayed.
 * @param block An optional block of code to be executed on the toast instance.
 */
inline fun Context.toastLong(
  @StringRes resource: Int,
  block: Toast.() -> Unit = {},
): Toast = toast(resource, Toast.LENGTH_LONG, block)

/**
 * Displays a long toast message with the given text.
 *
 * @param text The text to be displayed in the toast message.
 * @param block An optional block of code to be executed on the toast instance.
 */
inline fun Context.toastLong(
  text: String,
  block: Toast.() -> Unit = {},
): Toast = toast(text, Toast.LENGTH_LONG, block)
