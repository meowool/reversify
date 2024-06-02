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

@file:Suppress("MemberVisibilityCanBePrivate")

package com.meowool.reflectify.common.android

import android.content.Context
import android.content.res.Configuration
import it.unimi.dsi.fastutil.ints.Int2ObjectLinkedOpenHashMap

/**
 * A type alias for language tags, represented as strings.
 */
typealias LanguageTag = String

/**
 * An object that defines commonly used language tags.
 */
@Suppress("SpellCheckingInspection")
object LanguageTags {
  const val English = "en"
  const val Chinese = "zh"
  const val SimplifiedChinese = "zh-Hans"
  const val TraditionalChinese = "zh-Hant"

  /**
   * Checks if the given language tag matches the device's language configuration.
   *
   * @param configuration The device's language configuration.
   * @param languageTag The language tag to check.
   * @return `true` if the language tag matches the device's language configuration, `false` otherwise.
   */
  fun isMatches(configuration: Configuration, languageTag: LanguageTag): Boolean {
    val locale = configuration.locales[0]

    debug {
      """
      (languageTag=$languageTag)
      language: ${locale.language}
      languageTag: ${locale.toLanguageTag()}
      country: ${locale.country}
      script: ${locale.script}
      displayScript: ${locale.displayScript}
      string: $locale
      """.trimIndent()
    }

    if (locale.language == languageTag) return true

    val localeLanguageTag = locale.toLanguageTag()
    if (localeLanguageTag == languageTag) return true
    if (localeLanguageTag.startsWith(languageTag)) return true

    // Compatibility processing
    when (languageTag) {
      SimplifiedChinese -> {
        if (locale.language == "zh" && locale.script == "Hans") return true
        if (localeLanguageTag.startsWith("zh-CN")) return true
        if (localeLanguageTag.startsWith("zh-SG")) return true
      }
      TraditionalChinese -> {
        if (locale.language == "zh" && locale.script == "Hant") return true
        if (localeLanguageTag.startsWith("zh-TW")) return true
        if (localeLanguageTag.startsWith("zh-HK")) return true
        if (localeLanguageTag.startsWith("zh-MO")) return true
      }
    }

    return false
  }
}

/**
 * An annotation class to mark sealed classes that contain localized strings.
 *
 * @property name The name of the sealed class.
 *   This will be the name of the generated extension property in [StringsFactory],
 *   defaults to the value set in the KSP argument.
 */
@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.SOURCE)
annotation class SealedStrings(val name: String = "")

/**
 * An annotation class to mark localized strings.
 *
 * The class must inherit the sealed class marked with the [SealedStrings] annotation.
 *
 * @property languageTag The language tag for the localized strings.
 * @property default Indicates whether this is the default localization.
 */
@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.SOURCE)
annotation class LocalizedStrings(
  val languageTag: LanguageTag,
  val default: Boolean = false,
)

/**
 * A factory class to access localized strings.
 *
 * All available strings should be extended in [StringsFactory].
 *
 * @property configuration The device's configuration.
 */
class StringsFactory(internal val configuration: Configuration) {
  internal val map = Int2ObjectLinkedOpenHashMap<Any>()

  /**
   * Creates a new instance of [StringsFactory].
   *
   * @param context The context to get the device's configuration.
   */
  constructor(context: Context) : this(context.resources.configuration)
}
