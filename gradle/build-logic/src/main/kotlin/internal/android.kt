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

package internal

import com.android.build.api.dsl.ApplicationDefaultConfig
import com.android.build.api.dsl.CommonExtension
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.get

internal typealias BaseExtension = CommonExtension<*, *, *, *, *, *>

@PublishedApi
internal inline fun Project.androidBaseExtension(block: BaseExtension.() -> Unit = {}) =
  (extensions["android"] as BaseExtension).apply(block)

internal fun BaseExtension.init(project: Project) = with(project) {
  namespace = projectNamespace
  compileSdk = 34
  defaultConfig {
    if (this is ApplicationDefaultConfig) {
      // We set the target sdk to the same as to compile sdk, so that we can be compatible
      // with new Android features. This is because we always choose higher Android APIs as
      // the basis for development, which helps to improve the experience of high-version
      // Android users.
      targetSdk = compileSdk
      versionName = project.version.toString()
    }
    minSdk = 26
    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
  }
  compileOptions {
    sourceCompatibility(JavaToolchainVersion)
    targetCompatibility(JavaToolchainVersion)
  }
  packaging.resources {
    excludes += "META-INF/LICENSE*"
  }
  dependencies {
    arrayOf(
      libs.androidx.core,
      libs.androidx.annotation,
    ).forEach { "implementation"(it) }
    // Use shared code for all android modules
    importShared(":common-android")
  }
}
