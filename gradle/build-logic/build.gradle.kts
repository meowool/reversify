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

plugins { `kotlin-dsl` }

dependencies {
  arrayOf(
    libs.gradle.kotlin.dsl,
    libs.kotlin.gradle.plugin,
    libs.android.gradle.plugin,
    libs.google.ksp.gradle.plugin,

    platform(libs.kotlin.bom),
    // A workaround to enable version catalog usage in the convention plugin,
    // see https://github.com/gradle/gradle/issues/15383#issuecomment-779893192
    files(libs.javaClass.superclass.protectionDomain.codeSource.location),
  ).forEach(::implementation)
}
