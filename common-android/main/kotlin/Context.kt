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

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.content.res.Configuration
import android.net.Uri
import android.view.inputmethod.InputMethodManager
import android.webkit.MimeTypeMap
import androidx.core.content.getSystemService

/**
 * Gets the screen width in pixels.
 */
inline val Context.screenWidth: Int get() = resources.displayMetrics.widthPixels

/**
 * Gets the screen height in pixels.
 */
inline val Context.screenHeight: Int get() = resources.displayMetrics.heightPixels

/**
 * Gets the screen density.
 */
inline val Context.screenDensity: Float get() = resources.displayMetrics.density

/**
 * Gets the screen density in dots per inch (DPI).
 */
inline val Context.screenDensityDpi: Int get() = resources.displayMetrics.densityDpi

/**
 * Determines whether the device is in portrait orientation.
 */
inline val Context.isPortrait: Boolean
  get() = resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT

/**
 * Retrieves the [InputMethodManager] service from the current [Context].
 * If the service cannot be obtained, an error will be thrown.
 *
 * @return the [InputMethodManager] service instance.
 * @throws IllegalArgumentException if the service cannot be obtained.
 */
inline val Context.inputMethodManager: InputMethodManager
  get() = getSystemService() ?: error("Could not get InputMethodManager")

/**
 * Finds an [Activity] instance associated with the given [Context].
 *
 * If no [Activity] can be found, an [IllegalArgumentException] will be thrown.
 *
 * @return the found [Activity] instance.
 * @throws IllegalArgumentException if no [Activity] can be found.
 */

tailrec fun Context.findActivity(): Activity =
  when (this) {
    is Activity -> this
    is ContextWrapper -> this.baseContext.findActivity()
    else -> throw IllegalArgumentException("Could not find activity!")
  }

/**
 * Finds an [Activity] instance of the specified type [T] associated with the given [Context].
 *
 * If no [Activity] of the specified type can be found, an [IllegalArgumentException] will be thrown.
 *
 * @param T the type of [Activity] to find.
 * @return the found [Activity] instance of type [T].
 * @throws IllegalArgumentException if no [Activity] of type [T] can be found.
 */
@JvmName("findActivityByType")
inline fun <reified T : Activity> Context.findActivity(): T {
  var context = this
  while (true) {
    when (context) {
      is T -> return context
      is ContextWrapper -> context = context.baseContext
      else -> throw IllegalArgumentException("Could not find ${T::class.java}!")
    }
  }
}

/**
 * Resolves the file extension from the given [Uri].
 *
 * @param uri the [Uri] to resolve the file extension from.
 * @return the file extension as a [String], or `null` if it cannot be determined.
 */
fun Context.resolveFileExtension(uri: Uri): String? =
  contentResolver.getType(uri)?.let { MimeTypeMap.getSingleton().getExtensionFromMimeType(it) }
    ?: MimeTypeMap.getFileExtensionFromUrl(uri.toString()).takeIf { it.isNotEmpty() }
