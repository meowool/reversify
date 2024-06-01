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
  "FunctionName",
  "MemberVisibilityCanBePrivate",
  "SpellCheckingInspection",
  "RemoveExplicitTypeArguments",
  "NOTHING_TO_INLINE",
)

package com.meowool.reflectify

import com.meowool.reflectify.builtin.int
import com.meowool.reflectify.builtin.intArray
import com.meowool.reflectify.common.cast
import com.meowool.reflectify.common.castOrNull
import com.meowool.reflectify.internal.UnknownType
import com.meowool.reflectify.internal.toFullyQualifiedName
import com.meowool.reflectify.internal.toJvmTypeDescriptor
import kotlin.reflect.KClass
import java.lang.Class.forName as classOf
import java.lang.reflect.Array.newInstance as newArray

/**
 * Safe type representation of a runtime class.
 */
@JvmInline
value class Type<T> @PublishedApi internal constructor(private val actual: Any) {
  /**
   * A nullable version of this type.
   *
   * This helps the IDE to infer the type better.
   */
  val nullable: Type<T?> get() = Type(actual)

  /**
   * Wraps this type into an array type.
   *
   * The result always returns an array type of the boxed object type, even for primitive types.
   * For example, if this type is [C.int][int], it will be represented as `[Ljava/lang/Integer;`
   * in bytecode instead of `[I`. To represent primitive type arrays directly, use specialized
   * types like [C.intArray][intArray].
   *
   * ## Performance
   *
   * While functionally equivalent to `Array<elementType>::class.type`, this property is less
   * performant. Therefore, it's recommended to use it only when direct access to the type is
   * unavailable.
   *
   * For instance, prefer using `Array<View>::class.type` over `C.view.array`.
   */
  val array: Type<Array<T>> get() = Type<Array<T>>(newArray(javaClass, 0).javaClass)

  /**
   * Tries to return the actual type value as an instance of the java runtime class.
   */
  val javaClass: Class<T> get() = actual.castOrNull<Class<T>>() ?: classOf(qualifiedName).cast()

  /**
   * Returns the class qualified name of this type.
   */
  val qualifiedName: String get() = (actual as? String ?: javaClass.name).toFullyQualifiedName()

  /**
   * Returns the class descriptor of this type.
   */
  val descriptor: String get() = (actual as? String ?: javaClass.name).toJvmTypeDescriptor()

  /**
   * Whether this type is unknown.
   *
   * @see Type.Unknown
   */
  val isUnknown: Boolean get() = actual === UnknownType

  override fun toString(): String = actual.toString()

  companion object {
    /**
     * Represents an unknown type.
     *
     * This can be useful when dealing with members whose signature types are uncertain.
     * Reflectify will attempt to match the closest member when encountering an unknown type.
     */
    val Unknown: Type<*> = Type<Any?>(UnknownType)
  }
}

/**
 * Type representation of a Java runtime class.
 *
 * @param value Java class corresponding to type.
 */
inline fun <T> Type(value: Class<out T>): Type<T> = Type(actual = value)

/**
 * Type representation of a Kotlin runtime class.
 *
 * @param value Kotlin class corresponding to type.
 */
inline fun <reified T : Any> Type(value: KClass<out T> = T::class): Type<T> = Type(actual = value.java)

/**
 * Type string representation of a runtime class.
 *
 * The type string can be in one of two formats:
 *
 * 1. JVM Type Descriptor ("Ljava/lang/Boolean;", "Z"):
 *    - For object types, the descriptor starts with "L" and ends with ";", e.g., "Landroid/view/View;"
 *    - For primitive types, the descriptor is a single character, e.g., "Z" (boolean), "I" (int)
 *    - For array types, the descriptor starts with "[" followed by the element type descriptor,
 *      e.g., "[Ljava/lang/String;" (string array), "[J" (long array), "[[I" (int[][])
 *    - For inner classes, the descriptor includes the outer class name and the inner class name
 *      separated by a "$", e.g., "Landroid/view/ViewGroup$LayoutParams;"
 *
 * 2. Fully Qualified Name ("java.lang.Boolean", "boolean"):
 *    - Represents the canonical name of the type, including package and outer class information.
 *
 * For the detailed relationship between "JVM Type Descriptors" and "Fully Qualified Names", please
 * refer to the [JVM Specification](https://docs.oracle.com/javase/specs/jvms/se7/html/jvms-4.html#jvms-4.2).
 *
 * @param value The type string representation, which can be a descriptor or a fully qualified name.
 * @param T The assignable type of the class corresponding to the type string.
 */
inline fun <T> Type(value: String): Type<T> = Type(actual = value)

/**
 * Type string representation of a runtime class.
 *
 * The type string can be in one of two formats:
 *
 * 1. JVM Type Descriptor ("Ljava/lang/Boolean;", "Z"):
 *    - For object types, the descriptor starts with "L" and ends with ";", e.g., "Landroid/view/View;"
 *    - For primitive types, the descriptor is a single character, e.g., "Z" (boolean), "I" (int)
 *    - For array types, the descriptor starts with "[" followed by the element type descriptor,
 *      e.g., "[Ljava/lang/String;" (string array), "[J" (long array), "[[I" (int[][])
 *    - For inner classes, the descriptor includes the outer class name and the inner class name
 *      separated by a "$", e.g., "Landroid/view/ViewGroup$LayoutParams;"
 *
 * 2. Fully Qualified Name ("java.lang.Boolean", "boolean"):
 *    - Represents the canonical name of the type, including package and outer class information.
 *
 * For the detailed relationship between "JVM Type Descriptors" and "Fully Qualified Names", please
 * refer to the [JVM Specification](https://docs.oracle.com/javase/specs/jvms/se7/html/jvms-4.html#jvms-4.2).
 *
 * @param value The type string representation, which can be a descriptor or a fully qualified name.
 */
@JvmName("AnyType")
inline fun Type(value: String): Type<Any> = Type(actual = value)

/**
 * Returns a [Type] from this [KClass].
 */
inline val <T : Any> KClass<out T>.type: Type<T> get() = Type(actual = this.java)

/**
 * Returns a [Type] from this [Class].
 */
inline val <T> Class<out T>.type: Type<T> get() = Type(actual = this)

/**
 * Returns a [Type] from this [String].
 *
 * The string can be in one of two formats:
 *
 * 1. JVM Type Descriptor ("Ljava/lang/Boolean;", "Z"):
 *    - For object types, the descriptor starts with "L" and ends with ";", e.g., "Landroid/view/View;"
 *    - For primitive types, the descriptor is a single character, e.g., "Z" (boolean), "I" (int)
 *    - For array types, the descriptor starts with "[" followed by the element type descriptor,
 *      e.g., "[Ljava/lang/String;" (string array), "[J" (long array), "[[I" (int[][])
 *    - For inner classes, the descriptor includes the outer class name and the inner class name
 *      separated by a "$", e.g., "Landroid/view/ViewGroup$LayoutParams;"
 *
 * 2. Fully Qualified Name ("java.lang.Boolean", "boolean"):
 *    - Represents the canonical name of the type, including package and outer class information.
 *
 * For the detailed relationship between "JVM Type Descriptors" and "Fully Qualified Names", please
 * refer to the [JVM Specification](https://docs.oracle.com/javase/specs/jvms/se7/html/jvms-4.html#jvms-4.2).
 */
inline val String.type: Type<Any> get() = Type(this)
