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

import internal.androidBaseExtension
import internal.init
import org.gradle.api.Project

val isCiEnvironment: Boolean get() = System.getenv("CI") != null

fun Project.cmakeVersion(): String = providers.gradleProperty("cmake.version").orNull
  ?: error("`cmake.version` property is not set in the gradle.properties file.")

fun Project.ndkVersion(): String = providers.gradleProperty("ndk.version").orNull
  ?: error("`cmake.version` property is not set in the gradle.properties file.")

fun Project.setupAndroid() = androidBaseExtension {
  init(this@setupAndroid)
}
