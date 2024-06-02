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

import android.os.Build.VERSION.SDK_INT
import android.os.Build.VERSION_CODES
import androidx.annotation.ChecksSdkIntAtLeast

/**
 * Utility object that provides convenience methods to check the current Android SDK version.
 */
object Versions {
  /**
   * Checks if the device is running at least Android 9.0 (Pie).
   *
   * @return `true` if the device's Android SDK version is at least 28 (Pie), `false` otherwise.
   */
  @ChecksSdkIntAtLeast(api = VERSION_CODES.P)
  inline fun atLeastPie(): Boolean = SDK_INT >= VERSION_CODES.P

  /**
   * Checks if the device is running at least Android 10 (Q).
   *
   * @return `true` if the device's Android SDK version is at least 29 (Q), `false` otherwise.
   */
  @ChecksSdkIntAtLeast(api = VERSION_CODES.Q)
  inline fun atLeastQ(): Boolean = SDK_INT >= VERSION_CODES.Q

  /**
   * Checks if the device is running at least Android 11 (R).
   *
   * @return `true` if the device's Android SDK version is at least 30 (R), `false` otherwise.
   */
  @ChecksSdkIntAtLeast(api = VERSION_CODES.R)
  inline fun atLeastR(): Boolean = SDK_INT >= VERSION_CODES.R

  /**
   * Checks if the device is running at least Android 12 (S).
   *
   * @return `true` if the device's Android SDK version is at least 31 (S), `false` otherwise.
   */
  @ChecksSdkIntAtLeast(api = VERSION_CODES.S)
  inline fun atLeastS(): Boolean = SDK_INT >= VERSION_CODES.S

  /**
   * Checks if the device is running at least Android 13 (Tiramisu).
   *
   * @return `true` if the device's Android SDK version is at least 33 (Tiramisu), `false` otherwise.
   */
  @ChecksSdkIntAtLeast(api = VERSION_CODES.TIRAMISU)
  inline fun atLeastTiramisu(): Boolean = SDK_INT >= VERSION_CODES.TIRAMISU

  /**
   * Checks if the device is running at least Android 14 (Upside Down Cake).
   *
   * @return `true` if the device's Android SDK version is at least 34 (Upside Down Cake), `false` otherwise.
   */
  @ChecksSdkIntAtLeast(api = VERSION_CODES.UPSIDE_DOWN_CAKE)
  inline fun atLeastUpsideDownCake(): Boolean = SDK_INT >= VERSION_CODES.UPSIDE_DOWN_CAKE
}
