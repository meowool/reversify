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

import com.meowool.reflectify.common.castOrNull
import android.accessibilityservice.AccessibilityService
import android.accessibilityservice.AccessibilityServiceInfo.FEEDBACK_ALL_MASK
import android.content.Context
import android.content.Context.ACCESSIBILITY_SERVICE
import android.content.pm.ServiceInfo
import android.media.projection.MediaProjectionManager
import android.view.accessibility.AccessibilityManager
import androidx.activity.contextaware.ContextAware
import androidx.activity.contextaware.ContextAwareHelper
import androidx.activity.contextaware.OnContextAvailableListener
import androidx.lifecycle.HasDefaultViewModelProviderFactory
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LifecycleService
import androidx.lifecycle.SAVED_STATE_REGISTRY_OWNER_KEY
import androidx.lifecycle.SavedStateViewModelFactory
import androidx.lifecycle.VIEW_MODEL_STORE_OWNER_KEY
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStore
import androidx.lifecycle.ViewModelStoreOwner
import androidx.lifecycle.enableSavedStateHandles
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.MutableCreationExtras
import androidx.savedstate.SavedStateRegistry
import androidx.savedstate.SavedStateRegistryController
import androidx.savedstate.SavedStateRegistryOwner

/**
 * Returns the system service of type [T] with the given [name].
 */
inline fun <reified T : Any> Context.systemService(name: String): T =
  getSystemService(name).castOrNull() ?: error("Service not found: $name")

/**
 * Retrieves the [MediaProjectionManager] system service.
 */
inline fun Context.mediaProjectionManager(): MediaProjectionManager =
  systemService(Context.MEDIA_PROJECTION_SERVICE)

/**
 * Checks if an Accessibility Service is enabled for the current application.
 *
 * This function retrieves the list of enabled Accessibility Services and checks if any
 * of them belongs to the current application's package.
 *
 * @return `true` if an Accessibility Service is enabled for the current application,
 * `false` otherwise.
 */
inline fun <reified T : AccessibilityService> Context.isAccessibilityServiceEnabled(): Boolean =
  systemService<AccessibilityManager>(ACCESSIBILITY_SERVICE)
    .getEnabledAccessibilityServiceList(FEEDBACK_ALL_MASK)
    .also { debug { "Enabled Accessibility Services: $it" } }
    .any { it.resolveInfo.serviceInfo.matches<T>() }

/**
 * Checks if the specified [T] Accessibility Service matches the provided [ServiceInfo].
 */
inline fun <reified T : AccessibilityService> ServiceInfo.matches(): Boolean =
  this.packageName == packageName && this.name == T::class.java.name

/**
 * An abstract class that extends [LifecycleService] and implements several interfaces such as
 * [ContextAware], [LifecycleOwner], [ViewModelStoreOwner], [HasDefaultViewModelProviderFactory],
 * and [SavedStateRegistryOwner].
 *
 * This class is designed to be a base class for services that require lifecycle management,
 * context awareness, view model handling, and saved state management.
 *
 * @see androidx.activity.ComponentActivity
 */
abstract class ComponentService : LifecycleService(),
  ContextAware,
  LifecycleOwner,
  ViewModelStoreOwner,
  HasDefaultViewModelProviderFactory,
  SavedStateRegistryOwner {
  private var cachedViewModelStore: ViewModelStore? = null
  private var cachedViewModelProviderFactory: ViewModelProvider.Factory? = null

  private val contextAwareHelper = ContextAwareHelper()
  private val savedStateRegistryController = SavedStateRegistryController.create(this)

  override val viewModelStore: ViewModelStore
    get() = cachedViewModelStore ?: ViewModelStore().also { cachedViewModelStore = it }

  override val defaultViewModelProviderFactory: ViewModelProvider.Factory
    get() = cachedViewModelProviderFactory ?: SavedStateViewModelFactory(
      application,
      this,
      null,
    ).also { cachedViewModelProviderFactory = it }

  override val defaultViewModelCreationExtras: CreationExtras
    get() = MutableCreationExtras().also { extras ->
      application?.let { extras[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] = it }
      extras[SAVED_STATE_REGISTRY_OWNER_KEY] = this
      extras[VIEW_MODEL_STORE_OWNER_KEY] = this
    }

  override val savedStateRegistry: SavedStateRegistry
    get() = savedStateRegistryController.savedStateRegistry

  init {
    savedStateRegistryController.performAttach()
    enableSavedStateHandles()
  }

  override fun onCreate() {
    super.onCreate()
    savedStateRegistryController.performRestore(null)
    contextAwareHelper.dispatchOnContextAvailable(this)
  }

  override fun onDestroy() {
    super.onDestroy()
    // Clear out the available context
    contextAwareHelper.clearAvailableContext()
    // And clear the ViewModelStore
    viewModelStore.clear()
  }

  override fun peekAvailableContext(): Context? =
    contextAwareHelper.peekAvailableContext()

  override fun addOnContextAvailableListener(listener: OnContextAvailableListener) =
    contextAwareHelper.addOnContextAvailableListener(listener)

  override fun removeOnContextAvailableListener(listener: OnContextAvailableListener) =
    contextAwareHelper.removeOnContextAvailableListener(listener)
}
