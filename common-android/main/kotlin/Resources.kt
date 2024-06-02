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

import android.content.res.Configuration
import android.content.res.Resources

/**
 * Checks if the system is currently in dark mode.
 *
 * @return `true` if the system is in dark mode, `false` otherwise.
 */
fun Resources.isSystemDarkMode(): Boolean {
  val mode = configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK
  return mode == Configuration.UI_MODE_NIGHT_YES
}
