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

enableFeaturePreview("STABLE_CONFIGURATION_CACHE")
enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

includeBuild("gradle/build-logic")

// Include all possible subprojects (excluding root project, hidden directories,
// and composite build directories)
// rootDir.walkTopDown()
//   .onEnter { it.name.first() != '.' && !it.path.endsWith("gradle/build-logic") }
//   .filter { it.isDirectory && it.resolve("build.gradle.kts").exists() }
//   .map { it.relativeTo(rootDir).path.replace(File.separatorChar, ':') }
//   .filter { it.isNotEmpty() }
//   .forEach { include(":$it") }
include(":common")
include(":common-android")
include(":reflectify:core")

pluginManagement.repositories {
  gradlePluginPortal()
  mavenCentral()
  google()
}

rootProject.name = "Reversify"
