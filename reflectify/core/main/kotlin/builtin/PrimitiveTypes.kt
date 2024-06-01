/*
 * Copyright (C) 2024 Meowool <https://github.com/meowool/graphs/contributors>
 *
 * This file is part of the Ping project <https://github.com/meowool/ping>.
 *
 * Ping is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * Ping is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with Ping.  If not, see <https://www.gnu.org/licenses/>.
 */

@file:Suppress("UnusedReceiverParameter", "PLATFORM_CLASS_MAPPED_TO_KOTLIN")

package com.meowool.reflectify.builtin

import com.meowool.reflectify.C
import com.meowool.reflectify.Type
import java.lang.Boolean as JavaBoolean
import java.lang.Byte as JavaByte
import java.lang.Character as JavaCharacter
import java.lang.Double as JavaDouble
import java.lang.Float as JavaFloat
import java.lang.Integer as JavaInteger
import java.lang.Long as JavaLong
import java.lang.Short as JavaShort
import java.lang.Void as JavaVoid

/**
 * The primitive type of [JavaVoid].
 *
 * This is represented as `V` in bytecode.
 *
 * @see JavaVoid.TYPE
 */
inline val C.Companion.void: Type<JavaVoid> get() = Type(JavaVoid.TYPE)

/**
 * The primitive type of [Boolean].
 *
 * This is represented as `Z` in bytecode.
 */
inline val C.Companion.boolean: Type<Boolean> get() = Type(JavaBoolean.TYPE)

/**
 * The primitive type of [Char].
 *
 * This is represented as `C` in bytecode.
 */
inline val C.Companion.char: Type<Char> get() = Type(JavaCharacter.TYPE)

/**
 * The primitive type of [Byte].
 *
 * This is represented as `B` in bytecode.
 */
inline val C.Companion.byte: Type<Byte> get() = Type(JavaByte.TYPE)

/**
 * The primitive type of [Short].
 *
 * This is represented as `S` in bytecode.
 */
inline val C.Companion.short: Type<Short> get() = Type(JavaShort.TYPE)

/**
 * The primitive type of [Int].
 *
 * This is represented as `I` in bytecode.
 */
inline val C.Companion.int: Type<Int> get() = Type(JavaInteger.TYPE)

/**
 * The primitive type of [Long].
 *
 * This is represented as `J` in bytecode.
 */
inline val C.Companion.long: Type<Long> get() = Type(JavaLong.TYPE)

/**
 * The primitive type of [Float].
 *
 * This is represented as `F` in bytecode.
 */
inline val C.Companion.float: Type<Float> get() = Type(JavaFloat.TYPE)

/**
 * The primitive type of [Double].
 *
 * This is represented as `D` in bytecode.
 */
inline val C.Companion.double: Type<Double> get() = Type(JavaDouble.TYPE)

/**
 * The type of an array of primitive type [Byte].
 *
 * This is represented as `[B` in bytecode.
 *
 * @see C.Companion.byte
 * @see ByteArray
 */
inline val C.Companion.byteArray: Type<ByteArray> get() = Type(ByteArray::class.java)

/**
 * The type of an array of primitive type [Char].
 *
 * This is represented as `[C` in bytecode.
 *
 * @see C.Companion.char
 * @see CharArray
 */
inline val C.Companion.charArray: Type<CharArray> get() = Type(CharArray::class.java)

/**
 * The type of an array of primitive type [Short].
 *
 * This is represented as `[S` in bytecode.
 *
 * @see C.Companion.short
 * @see ShortArray
 */
inline val C.Companion.shortArray: Type<ShortArray> get() = Type(ShortArray::class.java)

/**
 * The type of an array of primitive type [Int].
 *
 * This is represented as `[I` in bytecode.
 *
 * @see C.Companion.int
 * @see IntArray
 */
inline val C.Companion.intArray: Type<IntArray> get() = Type(IntArray::class.java)

/**
 * The type of an array of primitive type [Long].
 *
 * This is represented as `[J` in bytecode.
 *
 * @see C.Companion.long
 * @see LongArray
 */
inline val C.Companion.longArray: Type<LongArray> get() = Type(LongArray::class.java)

/**
 * The type of an array of primitive type [Float].
 *
 * This is represented as `[F` in bytecode.
 *
 * @see C.Companion.float
 * @see FloatArray
 */
inline val C.Companion.floatArray: Type<FloatArray> get() = Type(FloatArray::class.java)

/**
 * The type of an array of primitive type [Double].
 *
 * This is represented as `[D` in bytecode.
 *
 * @see C.Companion.double
 * @see DoubleArray
 */
inline val C.Companion.doubleArray: Type<DoubleArray> get() = Type(DoubleArray::class.java)

/**
 * The type of an array of primitive type [Boolean].
 *
 * This is represented as `[Z` in bytecode.
 *
 * @see C.Companion.boolean
 * @see BooleanArray
 */
inline val C.Companion.booleanArray: Type<BooleanArray> get() = Type(BooleanArray::class.java)
