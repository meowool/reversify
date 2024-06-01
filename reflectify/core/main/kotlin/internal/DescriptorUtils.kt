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

@file:Suppress("SpellCheckingInspection")

package com.meowool.reflectify.internal

/**
 * Returns the JVM Type Descriptor representation of this class name.
 */
internal fun String.toJvmTypeDescriptor(): String = when {
  isDescriptor(this) -> this
  isValidJavaType(this) -> javaTypeToDescriptor(this)
  else -> throw IllegalArgumentException("Unsupported class name: $this")
}

/**
 * Returns the Fully Qualified Name representation of this class name.
 */
internal fun String.toFullyQualifiedName(): String = when {
  isValidJavaType(this) -> this
  isDescriptor(this) -> descriptorToJavaType(this)
  else -> throw IllegalArgumentException("Unsupported class name: $this")
}

/**
 * Convert a type descriptor to a Java type name. Will also deobfuscate class names if a
 * class mapper is provided.
 *
 * @param descriptor type descriptor
 * @return Java type name
 */
fun descriptorToJavaType(descriptor: String): String = when (val c = descriptor.first()) {
  'L' -> {
    require(descriptor.last() == ';')
    descriptor.substring(1, descriptor.length - 1).replace(DESCRIPTOR_PACKAGE_SEPARATOR, JAVA_PACKAGE_SEPARATOR)
  }
  '[' -> descriptorToJavaType(descriptor.substring(1)) + "[]"
  else -> primitiveDescriptorToJavaType(c)
}

private fun primitiveDescriptorToJavaType(primitive: Char): String = when (primitive) {
  'V' -> "void"
  'Z' -> "boolean"
  'B' -> "byte"
  'S' -> "short"
  'C' -> "char"
  'I' -> "int"
  'J' -> "long"
  'F' -> "float"
  'D' -> "double"
  else -> error("Unknown type $primitive")
}

/**
 * Convert a Java type name to a descriptor string.
 *
 * @param typeName the java type name
 * @return the descriptor string
 */
private fun javaTypeToDescriptor(typeName: String): String {
  require(typeName.indexOf(DESCRIPTOR_PACKAGE_SEPARATOR) == -1)
  return internalToDescriptor(typeName, ignorePrimitives = false)
}

private fun internalToDescriptor(typeName: String, ignorePrimitives: Boolean): String {
  if (!ignorePrimitives) when (typeName) {
    "void" -> return "V"
    "boolean" -> return "Z"
    "byte" -> return "B"
    "short" -> return "S"
    "char" -> return "C"
    "int" -> return "I"
    "long" -> return "J"
    "float" -> return "F"
    "double" -> return "D"
  }

  if (typeName.endsWith("[]")) {
    return "[" + internalToDescriptor(typeName.substring(0, typeName.length - 2), ignorePrimitives)
  }

  // Must be an object type.
  return "L" + typeName.replace(JAVA_PACKAGE_SEPARATOR, DESCRIPTOR_PACKAGE_SEPARATOR) + ";"
}

private fun isDescriptor(descriptor: String): Boolean = isClassDescriptor(descriptor) ||
  isPrimitiveDescriptor(descriptor) ||
  isArrayDescriptor(descriptor)

private fun isArrayDescriptor(descriptor: String): Boolean {
  if (descriptor.length < 2) return false
  if (descriptor.first() == '[') return isDescriptor(descriptor.substring(1))
  return false
}

private fun isPrimitiveDescriptor(descriptor: String): Boolean {
  if (descriptor.length != 1) return false
  val c = descriptor[0]
  return c == 'Z' || c == 'B' || c == 'S' || c == 'C' || c == 'I' || c == 'F' || c == 'J' || c == 'D'
}

private fun isClassDescriptor(descriptor: String): Boolean {
  val buffer = descriptor.toCharArray()
  val length = buffer.size
  if (length < 3 || buffer[0] != 'L') return false

  var pos = 1
  var ch: Char
  do {
    // First letter of an Ident (an Ident can't be empty)
    if (pos >= length) {
      return false
    }
    ch = buffer[pos++]
    if (isInvalidChar(ch) || ch == DESCRIPTOR_PACKAGE_SEPARATOR || ch == ';') {
      return false
    }
    // Next letters of an Ident
    do {
      if (pos >= length) {
        return false
      }
      ch = buffer[pos++]
      if (isInvalidChar(ch)) {
        return false
      }
    } while (ch != DESCRIPTOR_PACKAGE_SEPARATOR && ch != ';')
  } while (ch != ';')
  return pos == length
}

private fun isInvalidChar(ch: Char): Boolean = when (ch) {
  JAVA_PACKAGE_SEPARATOR, '[' -> true
  else -> false
}

/**
 * Checks the given [typeName] is a valid jvms binary name or not (jvms 4.2.1).
 *
 * @param typeName the jvms binary name
 * @return true if and only if the given type name is valid jvms binary name
 */
private fun isValidJavaType(typeName: String): Boolean {
  if (typeName.isEmpty()) return false
  var last: Char = 0.toChar()
  for (i in typeName.indices) {
    val c = typeName[i]
    if (c == ';' || c == '[' || c == '/') return false
    if (c == '.' && (i == 0 || last == '.')) return false
    last = c
  }
  return true
}

private const val DESCRIPTOR_PACKAGE_SEPARATOR = '/'
private const val JAVA_PACKAGE_SEPARATOR = '.'
