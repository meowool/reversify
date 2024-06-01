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

@file:Suppress(
  "SpellCheckingInspection",
  "UnusedReceiverParameter",
  "RemoveExplicitTypeArguments",
  "PLATFORM_CLASS_MAPPED_TO_KOTLIN",
)

package com.meowool.reflectify.builtin

import com.meowool.reflectify.C
import com.meowool.reflectify.Type
import kotlin.Boolean
import kotlin.Byte
import kotlin.Double
import kotlin.Float
import kotlin.Long
import kotlin.Short
import kotlin.String
import java.lang.Boolean as JavaBoolean
import java.lang.Byte as JavaByte
import java.lang.Character as JavaCharacter
import java.lang.Double as JavaDouble
import java.lang.Float as JavaFloat
import java.lang.Integer as JavaInteger
import java.lang.Long as JavaLong
import java.lang.Object as JavaObject
import java.lang.Short as JavaShort
import java.lang.String as JavaString
import java.lang.Void as JavaVoid

/**
 * The Java object type of [Any], which is the most basic root class type in the Java world.
 *
 * This is represented as `Ljava/lang/Object;` in bytecode.
 */
inline val C.Companion.Object: Type<Any> get() = Type(JavaObject::class.java)

/**
 * The object type of [JavaVoid].
 *
 * This is represented as `Ljava/lang/Void;` in bytecode.
 */
inline val C.Companion.Void: Type<JavaVoid> get() = Type(JavaVoid::class.java)

/**
 * The object (boxed) type of [Boolean].
 *
 * This is represented as `Ljava/lang/Boolean;` in bytecode.
 */
inline val C.Companion.Boolean: Type<Boolean> get() = Type<Boolean>(JavaBoolean::class.java)

/**
 * The object (boxed) type of [Char].
 *
 * This is represented as `Ljava/lang/Character;` in bytecode.
 */
inline val C.Companion.Char: Type<Char> get() = Type<Char>(JavaCharacter::class.java)

/**
 * The object (boxed) type of [Byte].
 *
 * This is represented as `Ljava/lang/Byte;` in bytecode.
 */
inline val C.Companion.Byte: Type<Byte> get() = Type<Byte>(JavaByte::class.java)

/**
 * The object (boxed) type of [Short].
 *
 * This is represented as `Ljava/lang/Short;` in bytecode.
 */
inline val C.Companion.Short: Type<Short> get() = Type<Short>(JavaShort::class.java)

/**
 * The object (boxed) type of [Int].
 *
 * This is represented as `Ljava/lang/Integer;` in bytecode.
 */
inline val C.Companion.Int: Type<Int> get() = Type<Int>(JavaInteger::class.java)

/**
 * The object (boxed) type of [Long].
 *
 * This is represented as `Ljava/lang/Long;` in bytecode.
 */
inline val C.Companion.Long: Type<Long> get() = Type<Long>(JavaLong::class.java)

/**
 * The object (boxed) type of [Float].
 *
 * This is represented as `Ljava/lang/Float;` in bytecode.
 */
inline val C.Companion.Float: Type<Float> get() = Type<Float>(JavaFloat::class.java)

/**
 * The object (boxed) type of [Double].
 *
 * This is represented as `Ljava/lang/Double;` in bytecode.
 */
inline val C.Companion.Double: Type<Double> get() = Type<Double>(JavaDouble::class.java)

/**
 * The object type of [String].
 *
 * This is represented as `Ljava/lang/String;` in bytecode.
 */
inline val C.Companion.String: Type<String> get() = Type<String>(JavaString::class.java)
