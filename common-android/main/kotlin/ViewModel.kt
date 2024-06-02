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

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn

/**
 * Converts the source [Flow] into a [StateFlow] and associates it with the [ViewModel]'s lifecycle.
 *
 * The [started] parameter controls when the [Flow] starts collecting. The recommended value is
 * `SharingStarted.WhileSubscribed(5_000)`, which will start collecting the [Flow] when the
 * [StateFlow] has at least one active collector, and stop collecting after 5 seconds of no active
 * collectors. This recommendation is based on the [article](https://medium.com/androiddevelopers/consuming-flows-safely-in-jetpack-compose-cde014d0d5a3#7f9e).
 *
 * This helps conserve resources, such as network connections or database queries, when the
 * [ViewModel] is not actively being used, for example, when the associated UI is not visible.
 *
 * @param T The type of the values emitted by the [Flow].
 * @param initialValue The initial value of the [StateFlow].
 * @param started The [SharingStarted] strategy to use for starting the collection of the [Flow].
 * @return A [StateFlow] that represents the latest value emitted by the [Flow].
 */
context(ViewModel)
inline fun <T> Flow<T>.stateInScope(
  initialValue: T,
  started: SharingStarted = SharingStarted.WhileSubscribed(5_000),
): StateFlow<T> = stateIn(viewModelScope, started, initialValue)

/**
 * Converts the source [Flow] into a [StateFlow] and associates it with the [ViewModel]'s lifecycle.
 *
 * This function uses the [SharingStarted.Eagerly] strategy to immediately start collecting the
 * [Flow], regardless of whether there are any active collectors. This is in contrast to the
 * [SharingStarted.WhileSubscribed] strategy, which only starts collecting when there is at least
 * one active collector.
 *
 * The eager collection strategy is useful when you want to ensure that the [Flow] is always
 * collecting, even when there are no active collectors. This can be important if the [Flow]
 * represents a critical or long-running operation that should not be interrupted.
 *
 * However, keep in mind that using the eager strategy may consume more resources, as the [Flow]
 * will continue to collect even when the associated UI is not visible. Consider the trade-offs
 * between resource usage and ensuring the continuity of the operation when deciding which
 * strategy to use.
 *
 * @param T The type of the values emitted by the [Flow].
 * @param initialValue The initial value of the [StateFlow].
 * @return A [StateFlow] that represents the latest value emitted by the [Flow].
 */
context(ViewModel)
inline fun <T> Flow<T>.eagerStateInScope(initialValue: T): StateFlow<T> =
  stateIn(viewModelScope, SharingStarted.Eagerly, initialValue)

/**
 * Caches the emitted [PagingData] in the [ViewModel]'s scope, ensuring that the data is retained
 * across configuration changes.
 *
 * This is a convenience function that wraps the [cachedIn] operator, using the [ViewModel]'s scope
 * as the coroutine context.
 *
 * @return A [Flow] that caches the emitted [PagingData] in the [ViewModel]'s scope.
 */
context(ViewModel)
inline fun <T : Any> Flow<PagingData<T>>.cachedInScope(): Flow<PagingData<T>> =
  cachedIn(viewModelScope)
