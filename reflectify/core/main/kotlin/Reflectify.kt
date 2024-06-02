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

package com.meowool.reflectify

import com.meowool.reflectify.internal.ContractImpl

abstract class Reflectify(val targetClass: String) {
  protected val contract: Contract = ContractImpl()

  interface Contract {
    fun <T> get(): T
    fun <T> get(type: Type<T>): T
    fun <T> get(name: String): T
    fun <T> get(name: String, type: Type<T>): T

    fun <T> set(value: T)
    fun <T> set(type: Type<T>, value: T)
    fun <T> set(name: String, value: T)
    fun <T> set(name: String, type: Type<T>, value: T)

    fun <R> call(vararg arguments: Any): R
    fun <R> call(returns: Type<R>, vararg arguments: Any): R
    fun <R> call(parameters: Array<Type<*>>, vararg arguments: Any): R
    fun <R> call(parameters: Array<Type<*>>, returns: Type<R>, vararg arguments: Any): R
    fun <R> call(name: String, vararg arguments: Any): R
    fun <R> call(name: String, returns: Type<R>, vararg arguments: Any): R
    fun <R> call(name: String, parameters: Array<Type<*>>, vararg arguments: Any): R
    fun <R> call(name: String, parameters: Array<Type<*>>, returns: Type<R>, vararg arguments: Any): R
  }
}
