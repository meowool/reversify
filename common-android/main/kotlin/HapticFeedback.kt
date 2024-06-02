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

import android.R
import android.os.Build.VERSION.SDK_INT
import android.os.Build.VERSION_CODES.O_MR1
import android.os.Build.VERSION_CODES.UPSIDE_DOWN_CAKE
import android.view.HapticFeedbackConstants.CLOCK_TICK
import android.view.HapticFeedbackConstants.FLAG_IGNORE_GLOBAL_SETTING
import android.view.HapticFeedbackConstants.KEYBOARD_TAP
import android.view.HapticFeedbackConstants.SEGMENT_TICK
import android.view.HapticFeedbackConstants.TEXT_HANDLE_MOVE
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ComponentActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlin.random.Random

inline val hapticFeedback get() = HapticFeedback

/**
 * An object used to manage haptic feedback events in the application.
 */
object HapticFeedback {
  internal val event = MutableStateFlow(Event(-1))

  /**
   * Triggers a haptic feedback event suitable for tapping a key on the keyboard.
   */
  fun tap() = emit(KEYBOARD_TAP)

  /**
   * Triggers a haptic feedback event suitable for switching segments.
   */
  fun switchSegment() = emit(if (SDK_INT >= UPSIDE_DOWN_CAKE) SEGMENT_TICK else CLOCK_TICK)

  /**
   * Triggers a haptic feedback event suitable for moving a text handle.
   */
  fun moveTextHandle() = emit(if (SDK_INT >= O_MR1) TEXT_HANDLE_MOVE else CLOCK_TICK)

  private fun emit(event: Int) {
    this.event.value = Event(event)
  }

  /**
   * Represents a haptic feedback event.
   */
  internal class Event(private val value: Int) {
    fun handle(view: View) {
      view.handleHapticFeedback(value)
    }

    @Suppress("EqualsAlwaysReturnsTrueOrFalse")
    override fun equals(other: Any?): Boolean = false
    override fun hashCode(): Int = Random.nextInt()
  }
}

/**
 * Handles the playback of a haptic feedback event on a view.
 *
 * @param feedbackConstant The haptic feedback constant to be played.
 * @see View.performHapticFeedback
 */
fun View.handleHapticFeedback(feedbackConstant: Int) {
  @Suppress("DEPRECATION")
  if (feedbackConstant != -1) performHapticFeedback(feedbackConstant, FLAG_IGNORE_GLOBAL_SETTING)
}

/**
 * Collects and handles haptic feedback events in a [ComponentActivity].
 *
 * This will only collect and handle haptic feedback events when the application is active.
 */
fun ComponentActivity.collectHapticFeedback() = lifecycleScope.launch {
  repeatOnLifecycle(Lifecycle.State.RESUMED) {
    hapticFeedback.event.collectLatest {
      debug { "HapticFeedback: $it" }
      it.handle(window.decorView.findViewById<ViewGroup>(R.id.content))
    }
  }
}
