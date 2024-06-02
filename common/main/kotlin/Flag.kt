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

package com.meowool.reflectify.common

/**
 * Adds the specified [flag] to this [Int] value.
 *
 * [For more details](https://en.wikipedia.org/wiki/Mask_(computing))
 *
 * @param flag The flag to be added.
 * @return The updated [Int] value with the specified [flag] added.
 */
inline fun Int.addFlag(flag: Int): Int = this or flag

/**
 * Removes the specified [flag] from this [Int] value.
 *
 * [For more details](https://en.wikipedia.org/wiki/Mask_(computing))
 *
 * @param flag The flag to be removed.
 * @return The updated [Int] value with the specified [flag] removed.
 */
inline fun Int.removeFlag(flag: Int): Int = this and flag.inv()

/**
 * Checks if the specified [flag] is set in this [Int] value.
 *
 * [For more details](https://en.wikipedia.org/wiki/Mask_(computing))
 *
 * @param flag The flag to be checked.
 * @return `true` if the specified [flag] is set, `false` otherwise.
 */
inline fun Int.hasFlag(flag: Int): Boolean = this and flag != 0
