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

@file:Suppress("NOTHING_TO_INLINE")

package com.meowool.reflectify.common.android

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import android.provider.Settings.ACTION_MANAGE_OVERLAY_PERMISSION
import androidx.activity.ComponentActivity
import androidx.activity.result.contract.ActivityResultContracts.StartActivityForResult

/**
 * Checks if the app has permission to draw overlays.
 */
inline fun Context.canDrawOverlays(): Boolean = Settings.canDrawOverlays(this)

/**
 * Requests the "SYSTEM_ALERT_WINDOW" permission, which allows the app to display windows on top
 * of other apps.
 *
 * This function first checks if the app already has the permission using the [canDrawOverlays] function.
 * If the permission is granted, it calls the [onGranted] lambda. If the permission is not granted, it
 * launches an activity for the user to manually grant the permission.
 *
 * After the user's interaction, the function checks the permission status again and calls either the
 * [onGranted] or [onDenied] lambda, depending on the result.
 *
 * The [onRequested] lambda is called before the permission request is initiated, which can be useful
 * for showing a UI element to the user indicating that a permission request is about to happen.
 *
 * @param onRequested A lambda that will be invoked when the permission request is initiated.
 * @param onGranted A lambda that will be invoked if the permission is granted.
 * @param onDenied A lambda that will be invoked if the permission is denied.
 */
inline fun ComponentActivity.requestDrawOverlaysPermission(
  crossinline onRequested: () -> Unit,
  crossinline onGranted: () -> Unit,
  crossinline onDenied: () -> Unit,
) {
  if (canDrawOverlays()) {
    onGranted()
  } else {
    val launcher = registerForActivityResult(StartActivityForResult()) { _ ->
      if (canDrawOverlays()) {
        onGranted()
      } else {
        onDenied()
      }
    }
    val intent = Intent(ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:$packageName"))
    launcher.launch(intent)
    onRequested()
  }
}
