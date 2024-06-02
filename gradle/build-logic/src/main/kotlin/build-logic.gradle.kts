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

import com.android.build.gradle.BasePlugin
import internal.androidBaseExtension
import internal.args
import internal.configureJvmToolchain
import internal.configureKotlinCompilationTask
import internal.importShared
import internal.libs
import internal.optIn
import internal.sonatypeSnapshots
import org.jetbrains.kotlin.gradle.dsl.KotlinProjectExtension
import org.jetbrains.kotlin.gradle.plugin.KotlinBasePlugin
import org.jetbrains.kotlin.gradle.plugin.KotlinMultiplatformPluginWrapper

allprojects {
  group = "com.meowool.reversify"

  repositories {
    google { filterAndroidDependencies(include = true) }
    mavenCentral { filterAndroidDependencies(include = false) }
    sonatypeSnapshots()
  }

  configureCompile()
  configureSourceSets()
  configureCleanTask()

  afterEvaluate {
    alignVersions()
    importCommonDependencies()
  }
}

/**
 * Cleaning the project.
 * We can delete all empty directories when running the root `clean` task,
 * and we have no concerns about this.
 */
fun configureCleanTask() = tasks.maybeCreate<Delete>("clean").apply {
  delete = projectDir.walk().filter {
    it.isDirectory &&
      it.name != ".git" &&
      it.name != ".idea" &&
      it.name != ".gradle" &&
      it.name != ".build" &&
      it.listFiles()?.isEmpty() == true
  }.toSet()
}

/**
 * We can skip the creation of the "src/" directory. Instead, this allows us to create
 * its subdirectories directly.
 */
fun Project.configureSourceSets() {
  // For Kotlin projects
  plugins.withType<KotlinBasePlugin> {
    extensions.configure<KotlinProjectExtension> {
      sourceSets.configureEach {
        resources.srcDir("$name/resources")
        kotlin.srcDir("$name/kotlin")
      }
    }
  }
  // For Android projects
  plugins.withType<BasePlugin> {
    androidBaseExtension {
      sourceSets.configureEach {
        fun configure(name: String, kotlinToJava: Boolean = false) {
          res.srcDir("$name/res")
          jniLibs.srcDir("$name/jni")
          assets.srcDir("$name/assets")
          resources.srcDir("$name/resources")
          manifest.srcFile("$name/AndroidManifest.xml")
          baselineProfiles.srcDir("$name/baseline")
          if (kotlinToJava) {
            java.srcDirs("$name/java", "$name/kotlin")
          } else {
            java.srcDir("$name/java")
            kotlin.srcDir("$name/kotlin")
          }
        }
        when (name) {
          "test" -> configure("unitTest", true)
          "androidTest" -> configure("instrumentTest", true)
          else -> configure(name)
        }
      }
    }
  }
  // For general JVM projects
  plugins.withType<JavaBasePlugin> {
    extensions.configure<SourceSetContainer> {
      configureEach {
        java.srcDir("$name/java")
        resources.srcDir("$name/resources")
      }
    }
  }
}

fun Project.configureCompile() {
  configureJvmToolchain()
  // Add experimental language features to Kotlin projects
  configureKotlinCompilationTask {
    args("-Xcontext-receivers")
    optIn("kotlin.contracts.ExperimentalContracts")
  }
}

/**
 * Use [BOM](https://docs.gradle.org/current/userguide/platforms.html#sub:bom_import)
 * to align version of dependencies of the same series.
 */
fun Project.alignVersions() {
  // For Kotlin multiplatform projects
  plugins.withType<KotlinMultiplatformPluginWrapper> {
    dependencies {
      "commonMainImplementation"(platform(libs.kotlin.bom))
    }
  }
  // For general JVM projects
  plugins.withType<JavaBasePlugin> {
    dependencies {
      "implementation"(platform(libs.kotlin.bom))
    }
  }
}

/**
 * Import common dependencies for all projects.
 */
fun Project.importCommonDependencies() {
  plugins.withType<JavaBasePlugin> {
    importShared(":common")
    arrayOf(
      libs.assertj,
      libs.assertk,
      libs.kotlin.test,
    ).forEach { dependencies.add("testImplementation", it) }
  }
}

/**
 * Filtering Android dependencies across different repositories can reduce network
 * requests and speed up Gradle build times. This is because we are aware that these
 * dependencies are only available in Google Maven repository.
 */
fun MavenArtifactRepository.filterAndroidDependencies(include: Boolean) = mavenContent {
  arrayOf(
    "androidx\\..*",
    "com\\.android.*",
    if (include) "com\\.google.*" else "com\\.google\\.testing\\..*",
  ).forEach { group ->
    if (include) includeGroupByRegex(group) else excludeGroupByRegex(group)
  }
}
