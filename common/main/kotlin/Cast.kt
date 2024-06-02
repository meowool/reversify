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

package com.meowool.reflectify.common

/**
 * Cast this object type to [T].
 *
 * @param T result type after conversion
 * @throws ClassCastException [T] type does not match with this object.
 */
@Throws(ClassCastException::class)
inline fun <reified T> Any?.cast(): T = this as T

/**
 * Cast this object type to [T] or return null if the type does not match.
 *
 * @param T result type after conversion
 */
inline fun <reified T> Any?.castOrNull(): T? = this as? T
