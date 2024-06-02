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

import android.app.Activity
import android.view.Window
import androidx.core.view.WindowInsetsControllerCompat

/**
 * Checks if the system is currently in dark mode.
 *
 * @return `true` if the system is in dark mode, `false` otherwise.
 */
inline fun Activity.isSystemDarkMode(): Boolean = resources.isSystemDarkMode()

/**
 * Configures the [Window] of the [Activity] to use edge-to-edge layout, making the content area
 * extend to the edge of the screen.
 *
 * @param statusBarDarkIcons `true` to use dark status bar icons, `false` to use light icons.
 * @param navigationBarDarkIcons `true` to use dark navigation bar icons, `false` to use light icons.
 * @return the [WindowInsetsControllerCompat] for this [Window].
 */
inline fun Activity.enableEdgeToEdge(
  statusBarDarkIcons: Boolean = false,
  navigationBarDarkIcons: Boolean = false,
): WindowInsetsControllerCompat = window.enableEdgeToEdge(
  statusBarDarkIcons,
  navigationBarDarkIcons,
)
