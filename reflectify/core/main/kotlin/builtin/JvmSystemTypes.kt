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
  "FunctionName",
  "RemoveRedundantQualifierName",
)

package com.meowool.reflectify.builtin

import com.meowool.reflectify.C
import com.meowool.reflectify.Type
import java.util.LinkedList

/**
 * The Java object type of [List] or [MutableList].
 *
 * This is represented as `Ljava/util/List;` in bytecode.
 */
inline val C.Companion.List: Type<MutableList<*>>
  get() = Type(MutableList::class.java)

/**
 * An alias for [C.Companion.List] with element type [T] constraints.
 */
inline fun <reified T> C.Companion.List(): Type<List<T>> =
  Type<List<T>>(MutableList::class.java)

/**
 * The Java object type of [ArrayList].
 *
 * This is represented as `Ljava/util/ArrayList;` in bytecode.
 */
inline val C.Companion.ArrayList: Type<ArrayList<*>>
  get() = Type(kotlin.collections.ArrayList::class.java)

/**
 * An alias for [C.Companion.ArrayList] with element type [T] constraints.
 */
inline fun <reified T> C.Companion.ArrayList(): Type<ArrayList<T>> =
  Type<ArrayList<T>>(kotlin.collections.ArrayList::class.java)

/**
 * The Java object type of [Set] or [MutableSet].
 *
 * This is represented as `Ljava/util/Set;` in bytecode.
 */
inline val C.Companion.Set: Type<MutableSet<*>>
  get() = Type(MutableSet::class.java)

/**
 * An alias for [C.Companion.Set] with element type [T] constraints.
 */
inline fun <reified T> C.Companion.Set(): Type<Set<T>> =
  Type<Set<T>>(MutableSet::class.java)

/**
 * The Java object type of [HashSet].
 *
 * This is represented as `Ljava/util/HashSet;` in bytecode.
 */
inline val C.Companion.HashSet: Type<HashSet<*>>
  get() = Type(kotlin.collections.HashSet::class.java)

/**
 * An alias for [C.Companion.HashSet] with element type [T] constraints.
 */
inline fun <reified T> C.Companion.HashSet(): Type<HashSet<T>> =
  Type<HashSet<T>>(kotlin.collections.HashSet::class.java)

/**
 * The Java object type of [Map] or [MutableMap].
 *
 * This is represented as `Ljava/util/Map;` in bytecode.
 */
inline val C.Companion.Map: Type<MutableMap<*, *>>
  get() = Type(MutableMap::class.java)

/**
 * An alias for [C.Companion.Map] with key type [K] and value type [V] constraints.
 */
inline fun <reified K, reified V> C.Companion.Map(): Type<Map<K, V>> =
  Type<Map<K, V>>(MutableMap::class.java)

/**
 * The Java object type of [HashMap].
 *
 * This is represented as `Ljava/util/HashMap;` in bytecode.
 */
inline val C.Companion.HashMap: Type<HashMap<*, *>>
  get() = Type(kotlin.collections.HashMap::class.java)

/**
 * An alias for [C.Companion.HashMap] with key type [K] and value type [V] constraints.
 */
inline fun <reified K, reified V> C.Companion.HashMap(): Type<HashMap<K, V>> =
  Type<HashMap<K, V>>(kotlin.collections.HashMap::class.java)

/**
 * The Java object type of [Iterable].
 *
 * This is represented as `Ljava/lang/Iterable;` in bytecode.
 */
inline val C.Companion.Iterable: Type<Iterable<*>>
  get() = Type(kotlin.collections.Iterable::class.java)

/**
 * An alias for [C.Companion.Iterable] with element type [T] constraints.
 */
inline fun <reified T> C.Companion.Iterable(): Type<Iterable<T>> =
  Type<Iterable<T>>(kotlin.collections.Iterable::class.java)

/**
 * The Java object type of [Collection] or [MutableCollection].
 *
 * This is represented as `Ljava/util/Collection;` in bytecode.
 */
inline val C.Companion.Collection: Type<MutableCollection<*>>
  get() = Type(MutableCollection::class.java)

/**
 * An alias for [C.Companion.Collection] with element type [T] constraints.
 */
inline fun <reified T> C.Companion.Collection(): Type<Collection<T>> =
  Type<Collection<T>>(MutableCollection::class.java)

/**
 * The Java object type of [LinkedList].
 *
 * This is represented as `Ljava/util/LinkedList;` in bytecode.
 */
inline val C.Companion.LinkedList: Type<LinkedList<*>>
  get() = Type(java.util.LinkedList::class.java)

/**
 * An alias for [C.Companion.LinkedList] with element type [T] constraints.
 */
inline fun <reified T> C.Companion.LinkedList(): Type<LinkedList<T>> =
  Type<LinkedList<T>>(java.util.LinkedList::class.java)

/**
 * The Java object type of [LinkedHashMap].
 *
 * This is represented as `Ljava/util/LinkedHashMap;` in bytecode.
 */
inline val C.Companion.LinkedHashMap: Type<LinkedHashMap<*, *>>
  get() = Type(kotlin.collections.LinkedHashMap::class.java)

/**
 * An alias for [C.Companion.LinkedHashMap] with key type [K] and value type [V] constraints.
 */
inline fun <reified K, reified V> C.Companion.LinkedHashMap(): Type<LinkedHashMap<K, V>> =
  Type<LinkedHashMap<K, V>>(kotlin.collections.LinkedHashMap::class.java)

/**
 * The Java object type of [LinkedHashSet].
 *
 * This is represented as `Ljava/util/LinkedHashSet;` in bytecode.
 */
inline val C.Companion.LinkedHashSet: Type<LinkedHashSet<*>>
  get() = Type(kotlin.collections.LinkedHashSet::class.java)

/**
 * An alias for [C.Companion.LinkedHashSet] with element type [T] constraints.
 */
inline fun <reified T> C.Companion.LinkedHashSet(): Type<LinkedHashSet<T>> =
  Type<LinkedHashSet<T>>(kotlin.collections.LinkedHashSet::class.java)

/**
 * The Java object type of [ListIterator].
 *
 * This is represented as `Ljava/util/ListIterator;` in bytecode.
 */
inline val C.Companion.ListIterator: Type<ListIterator<*>>
  get() = Type(kotlin.collections.ListIterator::class.java)

/**
 * An alias for [C.Companion.ListIterator] with element type [T] constraints.
 */
inline fun <reified T> C.Companion.ListIterator(): Type<ListIterator<T>> =
  Type<ListIterator<T>>(kotlin.collections.ListIterator::class.java)

/**
 * The Java object type of [Iterator].
 *
 * This is represented as `Ljava/util/Iterator;` in bytecode.
 */
inline val C.Companion.Iterator: Type<Iterator<*>>
  get() = Type(kotlin.collections.Iterator::class.java)

/**
 * An alias for [C.Companion.Iterator] with element type [T] constraints.
 */
inline fun <reified T> C.Companion.Iterator(): Type<Iterator<T>> =
  Type<Iterator<T>>(kotlin.collections.Iterator::class.java)

/**
 * The Java object type of [Comparable].
 *
 * This is represented as `Ljava/lang/Comparable;` in bytecode.
 */
inline val C.Companion.Comparable: Type<Comparable<*>>
  get() = Type(kotlin.Comparable::class.java)

/**
 * An alias for [C.Companion.Comparable] with element type [T] constraints.
 */
inline fun <reified T> C.Companion.Comparable(): Type<Comparable<T>> =
  Type<Comparable<T>>(kotlin.Comparable::class.java)

/**
 * The Java object type of [Comparator].
 *
 * This is represented as `Ljava/util/Comparator;` in bytecode.
 */
inline val C.Companion.Comparator: Type<Comparator<*>>
  get() = Type(java.util.Comparator::class.java)

/**
 * An alias for [C.Companion.Comparator] with element type [T] constraints.
 */
inline fun <reified T> C.Companion.Comparator(): Type<Comparator<T>> =
  Type<Comparator<T>>(java.util.Comparator::class.java)

/**
 * The Java object type of [java.util.SortedSet].
 *
 * This is represented as `Ljava/util/SortedSet;` in bytecode.
 */
inline val C.Companion.SortedSet: Type<java.util.SortedSet<*>>
  get() = Type(java.util.SortedSet::class.java)

/**
 * An alias for [C.Companion.SortedSet] with element type [T] constraints.
 */
inline fun <reified T> C.Companion.SortedSet(): Type<java.util.SortedSet<T>> =
  Type<java.util.SortedSet<T>>(java.util.SortedSet::class.java)

/**
 * The Java object type of [java.util.Queue].
 *
 * This is represented as `Ljava/util/Queue;` in bytecode.
 */
inline val C.Companion.Queue: Type<java.util.Queue<*>>
  get() = Type(java.util.Queue::class.java)

/**
 * An alias for [C.Companion.Queue] with element type [T] constraints.
 */
inline fun <reified T> C.Companion.Queue(): Type<java.util.Queue<T>> =
  Type<java.util.Queue<T>>(java.util.Queue::class.java)

/**
 * The Java object type of [Map.Entry] or [MutableMap.MutableEntry].
 *
 * This is represented as `Ljava/util/Map$Entry;` in bytecode.
 */
inline val C.Companion.Map_Entry: Type<MutableMap.MutableEntry<*, *>>
  get() = Type(MutableMap.MutableEntry::class.java)

/**
 * An alias for [C.Companion.Map_Entry] with key type [K] and value type [V] constraints.
 */
inline fun <reified K, reified V> C.Companion.Map_Entry(): Type<Map.Entry<K, V>> =
  Type<Map.Entry<K, V>>(MutableMap.MutableEntry::class.java)

/**
 * The Java object type of [java.util.AbstractQueue].
 *
 * This is represented as `Ljava/util/AbstractQueue;` in bytecode.
 */
inline val C.Companion.JavaAbstractQueue: Type<java.util.AbstractQueue<*>>
  get() = Type(java.util.AbstractQueue::class.java)

/**
 * An alias for [C.Companion.JavaAbstractQueue] with element type [T] constraints.
 */
inline fun <reified T> C.Companion.JavaAbstractQueue(): Type<java.util.AbstractQueue<T>> =
  Type<java.util.AbstractQueue<T>>(java.util.AbstractQueue::class.java)

/**
 * The object type of [kotlin.collections.AbstractList].
 *
 * This is represented as `Lkotlin/collections/AbstractList;` in bytecode.
 */
inline val C.Companion.KotlinAbstractList: Type<kotlin.collections.AbstractList<*>>
  get() = Type(kotlin.collections.AbstractList::class.java)

/**
 * An alias for [C.Companion.KotlinAbstractList] with element type [T] constraints.
 */
inline fun <reified T> C.Companion.KotlinAbstractList(): Type<kotlin.collections.AbstractList<T>> =
  Type<kotlin.collections.AbstractList<T>>(kotlin.collections.AbstractList::class.java)

/**
 * The Java object type of [java.util.AbstractList].
 *
 * This is represented as `Ljava/util/AbstractList;` in bytecode.
 */
inline val C.Companion.JavaAbstractList: Type<java.util.AbstractList<*>>
  get() = Type(java.util.AbstractList::class.java)

/**
 * An alias for [C.Companion.JavaAbstractList] with element type [T] constraints.
 */
inline fun <reified T> C.Companion.JavaAbstractList(): Type<java.util.AbstractList<T>> =
  Type<java.util.AbstractList<T>>(java.util.AbstractList::class.java)

/**
 * The object type of [kotlin.collections.AbstractCollection].
 *
 * This is represented as `Lkotlin/collections/AbstractList;` in bytecode.
 */
inline val C.Companion.KotlinAbstractCollection: Type<kotlin.collections.AbstractCollection<*>>
  get() = Type(kotlin.collections.AbstractCollection::class.java)

/**
 * An alias for [C.Companion.KotlinAbstractCollection] with element type [T] constraints.
 */
inline fun <reified T> C.Companion.KotlinAbstractCollection(): Type<kotlin.collections.AbstractCollection<T>> =
  Type<kotlin.collections.AbstractCollection<T>>(kotlin.collections.AbstractCollection::class.java)

/**
 * The Java object type of [java.util.AbstractCollection].
 *
 * This is represented as `Ljava/util/AbstractCollection;` in bytecode.
 */
inline val C.Companion.JavaAbstractCollection: Type<java.util.AbstractCollection<*>>
  get() = Type(java.util.AbstractCollection::class.java)

/**
 * An alias for [C.Companion.JavaAbstractCollection] with element type [T] constraints.
 */
inline fun <reified T> C.Companion.JavaAbstractCollection(): Type<java.util.AbstractCollection<T>> =
  Type<java.util.AbstractCollection<T>>(java.util.AbstractCollection::class.java)

/**
 * The object type of [kotlin.collections.AbstractSet].
 *
 * This is represented as `Lkotlin/collections/AbstractSet;` in bytecode.
 */
inline val C.Companion.KotlinAbstractSet: Type<kotlin.collections.AbstractSet<*>>
  get() = Type(kotlin.collections.AbstractSet::class.java)

/**
 * An alias for [C.Companion.KotlinAbstractSet] with element type [T] constraints.
 */
inline fun <reified T> C.Companion.KotlinAbstractSet(): Type<kotlin.collections.AbstractSet<T>> =
  Type<kotlin.collections.AbstractSet<T>>(kotlin.collections.AbstractSet::class.java)

/**
 * The Java object type of [java.util.AbstractSet].
 *
 * This is represented as `Ljava/util/AbstractSet;` in bytecode.
 */
inline val C.Companion.JavaAbstractSet: Type<java.util.AbstractSet<*>>
  get() = Type(java.util.AbstractSet::class.java)

/**
 * An alias for [C.Companion.JavaAbstractSet] with element type [T] constraints.
 */
inline fun <reified T> C.Companion.JavaAbstractSet(): Type<java.util.AbstractSet<T>> =
  Type<java.util.AbstractSet<T>>(java.util.AbstractSet::class.java)

/**
 * The object type of [kotlin.collections.AbstractMap].
 *
 * This is represented as `Lkotlin/collections/AbstractMap;` in bytecode.
 */
inline val C.Companion.KotlinAbstractMap: Type<kotlin.collections.AbstractMap<*, *>>
  get() = Type(kotlin.collections.AbstractMap::class.java)

/**
 * An alias for [C.Companion.KotlinAbstractMap] with key type [K] and value type [V] constraints.
 */
inline fun <reified K, reified V> C.Companion.KotlinAbstractMap(): Type<kotlin.collections.AbstractMap<K, V>> =
  Type<kotlin.collections.AbstractMap<K, V>>(kotlin.collections.AbstractMap::class.java)

/**
 * The Java object type of [java.util.AbstractMap].
 *
 * This is represented as `Ljava/util/AbstractMap;` in bytecode.
 */
inline val C.Companion.JavaAbstractMap: Type<java.util.AbstractMap<*, *>>
  get() = Type(java.util.AbstractMap::class.java)

/**
 * An alias for [C.Companion.JavaAbstractMap] with key type [K] and value type [V] constraints.
 */
inline fun <reified K, reified V> C.Companion.JavaAbstractMap(): Type<java.util.AbstractMap<K, V>> =
  Type<java.util.AbstractMap<K, V>>(java.util.AbstractMap::class.java)

/**
 * The object type of [kotlin.collections.AbstractMutableList].
 *
 * This is represented as `Lkotlin/collections/AbstractMutableList;` in bytecode.
 */
inline val C.Companion.KotlinAbstractMutableList: Type<kotlin.collections.AbstractMutableList<*>>
  get() = Type(kotlin.collections.AbstractMutableList::class.java)

/**
 * An alias for [C.Companion.KotlinAbstractMutableList] with element type [T] constraints.
 */
inline fun <reified T> C.Companion.KotlinAbstractMutableList(): Type<kotlin.collections.AbstractMutableList<T>> =
  Type<kotlin.collections.AbstractMutableList<T>>(kotlin.collections.AbstractMutableList::class.java)

/**
 * The Java object type of [java.util.Base64].
 *
 * This is represented as `Ljava/util/Base64;` in bytecode.
 */
inline val C.Companion.JavaBase64: Type<java.util.Base64>
  get() = Type(java.util.Base64::class.java)
