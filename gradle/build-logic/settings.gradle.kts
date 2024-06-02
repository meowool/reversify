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

import org.gradle.kotlin.dsl.support.expectedKotlinDslPluginsVersion

dependencyResolutionManagement {
  repositories {
    google {
      content {
        // We limit the scope of this repository to reduce network requests and
        // speed up Gradle build times.
        includeGroupByRegex("androidx\\..*")
        includeGroupByRegex("com\\.android.*")
        includeGroupByRegex("com\\.google\\.testing\\..*")
      }
    }
    gradlePluginPortal()
    mavenCentral()
  }
  versionCatalogs {
    create("libs") {
      from(files("../libs.versions.toml"))
      library(
        "gradle-kotlin-dsl",
        "org.gradle.kotlin:gradle-kotlin-dsl-plugins:$expectedKotlinDslPluginsVersion",
      )
    }
  }
}

rootProject.name = "build-logic"
