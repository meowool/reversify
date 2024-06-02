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

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.dsl.RepositoryHandler
import org.gradle.api.artifacts.repositories.MavenArtifactRepository
import org.gradle.api.plugins.JavaBasePlugin
import org.gradle.api.plugins.JavaPluginExtension
import org.gradle.jvm.toolchain.JavaLanguageVersion
import org.gradle.kotlin.dsl.DependencyHandlerScope
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.project
import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.dsl.KotlinProjectExtension
import org.jetbrains.kotlin.gradle.plugin.KotlinBasePlugin
import org.jetbrains.kotlin.gradle.tasks.KotlinCompilationTask

/**
 * We are using this constant to unify the Java toolchain version for the project,
 * as the source of truth.
 */
internal const val JavaToolchainVersion = 17

internal val Project.projectNamespace: String get() = buildString {
  append(rootProject.group)
  // :module:feature:left-right -> .module.feature.left.right
  append(project.path.replace("-", ".").replace(":", "."))
}

internal fun RepositoryHandler.sonatypeSnapshots(
  includeS01: Boolean = true,
  includeOld: Boolean = true,
  action: MavenArtifactRepository.() -> Unit = {},
) {
  if (includeS01) maven {
    name = "Sonatype OSS S01 Snapshots"
    setUrl("https://s01.oss.sonatype.org/content/repositories/snapshots")
    mavenContent { snapshotsOnly() }
    action()
  }
  if (includeOld) maven {
    name = "Sonatype OSS Snapshots"
    setUrl("https://oss.sonatype.org/content/repositories/snapshots")
    mavenContent { snapshotsOnly() }
    action()
  }
}

internal inline fun <reified Applied : Plugin<*>> Project.optIn(name: String) = plugins.withType<Applied> {
  optIn(name)
}

internal fun Project.optIn(name: String) = configureKotlinCompilationTask {
  compilerOptions.optIn.add(name)
}

internal fun Project.optIn(vararg names: String) = names.forEach {
  optIn(name = it)
}

internal fun Project.configureJvmToolchain() {
  plugins.withType<JavaBasePlugin> {
    configure<JavaPluginExtension> {
      toolchain.languageVersion.set(JavaLanguageVersion.of(JavaToolchainVersion))
    }
  }
  plugins.withType<KotlinBasePlugin> {
    configure<KotlinProjectExtension> {
      jvmToolchain(JavaToolchainVersion)
    }
  }
}

internal fun Project.configureKotlin(block: KotlinProjectExtension.() -> Unit) {
  plugins.withType<KotlinBasePlugin> { extensions.configure(block) }
}

internal fun Project.configureKotlinCompilationTask(configuration: KotlinCompilationTask<*>.() -> Unit) =
  configureKotlin { tasks.withType(configuration) }

internal fun KotlinCompilationTask<*>.args(vararg args: String) = compilerOptions {
  freeCompilerArgs.addAll(args.toList())
}

internal fun Project.rootFile(path: String) =
  rootProject.layout.projectDirectory.file(path).asFile

internal fun Project.importShared(
  modulePath: String,
  extraDependencies: (DependencyHandlerScope.() -> Unit)? = null,
) {
  if (project.path != modulePath) dependencies {
    "implementation"(project(modulePath))
    extraDependencies?.invoke(this)
  }
}
