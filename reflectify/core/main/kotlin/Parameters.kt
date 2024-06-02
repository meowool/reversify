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
  "NOTHING_TO_INLINE",
  "NON_PUBLIC_CALL_FROM_PUBLIC_INLINE",
  "FunctionOnlyReturningConstant",
  "unused"
)

package com.meowool.reflectify

/**
 * A series of type-safe parameter array wrappers with no memory overhead.
 */
sealed interface Parameters {
  /**
   * A parameter type array wrapper with single type-safe parameter constraint.
   */
  @JvmInline
  value class Single<A> internal constructor(val types: Array<Type<*>>) : Parameters

  /**
   * A parameter type array wrapper with two type-safe parameter constraints.
   */
  @JvmInline
  value class Two<A, B> internal constructor(val types: Array<Type<*>>) : Parameters

  /**
   * A parameter type array wrapper with three type-safe parameter constraints.
   */
  @JvmInline
  value class Three<A, B, C> internal constructor(val types: Array<Type<*>>) : Parameters

  /**
   * A parameter type array wrapper with four type-safe parameter constraints.
   */
  @JvmInline
  value class Four<A, B, C, D> internal constructor(val types: Array<Type<*>>) : Parameters

  /**
   * A parameter type array wrapper with five type-safe parameter constraints.
   */
  @JvmInline
  value class Five<A, B, C, D, E> internal constructor(val types: Array<Type<*>>) : Parameters

  /**
   * A parameter type array wrapper with six type-safe parameter constraints.
   */
  @JvmInline
  value class Six<A, B, C, D, E, F> internal constructor(val types: Array<Type<*>>) : Parameters

  /**
   * A parameter type array wrapper with seven type-safe parameter constraints.
   */
  @JvmInline
  value class Seven<A, B, C, D, E, F, G> internal constructor(val types: Array<Type<*>>) : Parameters

  /**
   * A parameter type array wrapper with eight type-safe parameter constraints.
   */
  @JvmInline
  value class Eight<A, B, C, D, E, F, G, H> internal constructor(val types: Array<Type<*>>) : Parameters

  /**
   * A parameter type array wrapper with nine type-safe parameter constraints.
   */
  @JvmInline
  value class Nine<A, B, C, D, E, F, G, H, I> internal constructor(val types: Array<Type<*>>) : Parameters

  /**
   * A parameter type array wrapper with ten type-safe parameter constraints.
   */
  @JvmInline
  value class Ten<A, B, C, D, E, F, G, H, I, J> internal constructor(val types: Array<Type<*>>) : Parameters

  /**
   * A parameter type array wrapper without type-safe parameter constraints.
   */
  @JvmInline
  value class Var internal constructor(val types: Array<Type<*>>) : Parameters
}

/**
 * Returns an empty parameter type array.
 *
 * @see emptyArray
 */
inline fun emptyParameters(): Parameters.Var = Parameters.Var(emptyArray())

/**
 * Represents an unknown parameter list.
 *
 * This can be useful when dealing with methods whose parameter list is uncertain.
 * Reflectify will attempt to match the closest method when encountering an unknown parameter list.
 *
 * @see Type.Unknown
 */
inline fun unknownParameters(): Parameters.Var? = null

/**
 * Returns a parameter type array with only the given type.
 */
inline fun <A> parametersOf(type: Type<A>): Parameters.Single<A> = Parameters.Single(arrayOf(type))

/**
 * Returns a parameter type array with the given types.
 */
inline fun <A, B> parametersOf(
  type1: Type<A>,
  type2: Type<B>,
): Parameters.Two<A, B> = Parameters.Two(arrayOf(type1, type2))

/**
 * Returns a parameter type array with the given types.
 */
inline fun <A, B, C> parametersOf(
  type1: Type<A>,
  type2: Type<B>,
  type3: Type<C>,
): Parameters.Three<A, B, C> = Parameters.Three(arrayOf(type1, type2, type3))

/**
 * Returns a parameter type array with the given types.
 */
inline fun <A, B, C, D> parametersOf(
  type1: Type<A>,
  type2: Type<B>,
  type3: Type<C>,
  type4: Type<D>,
): Parameters.Four<A, B, C, D> = Parameters.Four(arrayOf(type1, type2, type3, type4))

/**
 * Returns a parameter type array with the given types.
 */
inline fun <A, B, C, D, E> parametersOf(
  type1: Type<A>,
  type2: Type<B>,
  type3: Type<C>,
  type4: Type<D>,
  type5: Type<E>,
): Parameters.Five<A, B, C, D, E> = Parameters.Five(arrayOf(type1, type2, type3, type4, type5))

/**
 * Returns a parameter type array with the given types.
 */
inline fun <A, B, C, D, E, F> parametersOf(
  type1: Type<A>,
  type2: Type<B>,
  type3: Type<C>,
  type4: Type<D>,
  type5: Type<E>,
  type6: Type<F>,
): Parameters.Six<A, B, C, D, E, F> = Parameters.Six(arrayOf(type1, type2, type3, type4, type5, type6))

/**
 * Returns a parameter type array with the given types.
 */
inline fun <A, B, C, D, E, F, G> parametersOf(
  type1: Type<A>,
  type2: Type<B>,
  type3: Type<C>,
  type4: Type<D>,
  type5: Type<E>,
  type6: Type<F>,
  type7: Type<G>,
): Parameters.Seven<A, B, C, D, E, F, G> = Parameters.Seven(arrayOf(type1, type2, type3, type4, type5, type6, type7))

/**
 * Returns a parameter type array with the given types.
 */
inline fun <A, B, C, D, E, F, G, H> parametersOf(
  type1: Type<A>,
  type2: Type<B>,
  type3: Type<C>,
  type4: Type<D>,
  type5: Type<E>,
  type6: Type<F>,
  type7: Type<G>,
  type8: Type<H>,
): Parameters.Eight<A, B, C, D, E, F, G, H> = Parameters.Eight(arrayOf(type1, type2, type3, type4, type5, type6, type7, type8))

/**
 * Returns a parameter type array with the given types.
 */
inline fun <A, B, C, D, E, F, G, H, I> parametersOf(
  type1: Type<A>,
  type2: Type<B>,
  type3: Type<C>,
  type4: Type<D>,
  type5: Type<E>,
  type6: Type<F>,
  type7: Type<G>,
  type8: Type<H>,
  type9: Type<I>,
): Parameters.Nine<A, B, C, D, E, F, G, H, I> = Parameters.Nine(arrayOf(type1, type2, type3, type4, type5, type6, type7, type8, type9))

/**
 * Returns a parameter type array with the given types.
 */
inline fun <A, B, C, D, E, F, G, H, I, J> parametersOf(
  type1: Type<A>,
  type2: Type<B>,
  type3: Type<C>,
  type4: Type<D>,
  type5: Type<E>,
  type6: Type<F>,
  type7: Type<G>,
  type8: Type<H>,
  type9: Type<I>,
  type10: Type<J>,
): Parameters.Ten<A, B, C, D, E, F, G, H, I, J> = Parameters.Ten(arrayOf(type1, type2, type3, type4, type5, type6, type7, type8, type9, type10))

/**
 * Returns a parameter type array with the given types.
 */
inline fun parametersOf(types: Array<Type<*>>): Parameters.Var = Parameters.Var(types)
