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

@file:Suppress("NOTHING_TO_INLINE")

package com.meowool.reflectify.common.android

import android.inputmethodservice.InputMethodService
import android.view.Window
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsControllerCompat

/**
 * Returns the [Window] associated with this [InputMethodService], or `null` if none.
 */
inline fun InputMethodService.windowOrNull(): Window? = this.window?.window

/**
 * Enables the safe area insets for the current window, ensuring that the content of
 * the window does not overlap with system bars.
 */
inline fun Window.keepSafeAreaInsets() = WindowCompat.setDecorFitsSystemWindows(this, true)

/**
 * Disables the safe area insets for the current window, allowing content to extend into
 * the areas occupied by system bars.
 */
inline fun Window.ignoreSafeAreaInsets() = WindowCompat.setDecorFitsSystemWindows(this, false)

/**
 * Returns a [WindowInsetsControllerCompat] instance associated with this [Window].
 */
inline fun Window.insetsController(): WindowInsetsControllerCompat =
  WindowInsetsControllerCompat(this, decorView)

/**
 * Configures the [Window] to use edge-to-edge layout, making the content area extend
 * to the edge of the screen.
 *
 * @param statusBarDarkIcons `true` to use dark status bar icons, `false` to use light icons.
 * @param navigationBarDarkIcons `true` to use dark navigation bar icons, `false` to use light icons.
 * @return the [WindowInsetsControllerCompat] for this [Window].
 */
fun Window.enableEdgeToEdge(
  statusBarDarkIcons: Boolean = false,
  navigationBarDarkIcons: Boolean = false,
): WindowInsetsControllerCompat {
  ignoreSafeAreaInsets()
  statusBarColor = 0
  navigationBarColor = 0
  return insetsController().apply {
    isAppearanceLightStatusBars = statusBarDarkIcons
    isAppearanceLightNavigationBars = navigationBarDarkIcons
  }
}
