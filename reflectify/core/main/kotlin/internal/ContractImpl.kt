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

package com.meowool.reflectify.internal

import com.meowool.reflectify.Reflectify
import com.meowool.reflectify.Type

internal class ContractImpl : Reflectify.Contract {
  override fun <T> get(name: String): T = compilerImplementation()
  override fun <T> get(name: String, type: Type<T>): T = compilerImplementation()
  override fun <T> set(name: String, value: T) = compilerImplementation()
  override fun <T> set(name: String, type: Type<T>, value: T) = compilerImplementation()
  override fun <R> call(name: String, vararg args: Any): R = compilerImplementation()
  override fun <R> call(name: String, returns: Type<R>, vararg arguments: Any): R = compilerImplementation()
  override fun <R> call(name: String, parameters: Array<Type<*>>, vararg arguments: Any): R = compilerImplementation()
  override fun <R> call(
    name: String,
    parameters: Array<Type<*>>,
    returns: Type<R>,
    vararg arguments: Any
  ): R = compilerImplementation()
}
