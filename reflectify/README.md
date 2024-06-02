# Reflectify

A high-performance reflection framework that leverages the **Kotlin compiler** and **Kotlin Symbol Processing (KSP)** to provide a seamless and efficient way to work with reflection in your Kotlin projects.

## Features

- **Highly Engineered**: Traditional reflection code can be verbose and difficult to manage. Reflectify solves this by allowing you to define a contract class for the class you want to reflect on. This contract serves as a standardized way to interact with the reflected class, improving readability and maintainability.
- **High Performance**: Reflection is notoriously slow in Java and Kotlin world. Reflectify is designed to address this issue. In many cases, it does not actually execute reflection at runtime. Instead, it replaces the contract class with direct method calls during the compilation process, resulting in zero-overhead performance.

## Installation

Reflectify is available on **Maven Central** and the **Gradle Plugin Portal**. To use it, you need to apply the Reflectify plugin to your project:

```kotlin
plugins {
  id("com.meowool.reflectify") version "1.0.0"
}
```

Alternatively, if you're using version catalog, you can add the following to your `libs.versions.toml` file:

```toml
[plugins]
reflectify = "com.meowool.reflectify:1.0.0"
```

And then apply the plugin using `alias`:

```kotlin
plugins {
  alias(libs.plugins.reflectify)
}
```

## Tutorial

### Table of Contents

- [Customizing Names](#customizing-names)
- [Reflecting on Hidden Types](#reflecting-on-hidden-types)
- [Reflecting on Static Members](#reflecting-on-static-members)
- [Defining Dynamic Signatures](#defining-dynamic-signatures)
- [Reflecting Mapping Types between Java and Kotlin](#reflecting-mapping-types-between-java-and-kotlin)

### Getting Started

> Reflectify allows you to define a **value class** that represents a contract of the class you want to reflect on, and Reflectify will generate the necessary code to handle the reflection behavior.

Let's take a look at a simple example.

Suppose we want to reflect on the `ContextImpl` class:

```java
package android.app;

class ContextImpl extends Context {
    ...
    private final Object mSync = new Object();
    ...
    /** @hide */
    @Override
    public String getOpPackageName() { ... }
    ...
}
```

We can define the following contract:

```kotlin
// Define the contract to mock a class to be reflected
@JvmInline
value class ContextImpl(val target: Any) : Reflectify(targetClass = "android.app.ContextImpl") {
  var mSync: Any
    set(value) = contract.set(value)
    get() = contract.get()

  fun getOpPackageName(): String = contract.call()
}
```

By calling the `contract.` functions provided by the contract, Reflectify will automatically replace them with the appropriate reflection logic during the compilation process. Here's a simplified pseudo-code of how it might work:

```kotlin
@JvmInline
value class ContextImpl(private target: android.app.ContextImpl) : Reflectify(...) {
  var mSync: Any
    set(value) = safeReflect(
      directCall = { target.mSync = value }，
      fallbackReflect = { target.setField("mSync", type = C.Object, value = value) },
    )
    get() = safeReflect(
      directCall = { target.mSync }，
      fallbackReflect = { target.getField("mSync", type = C.Object) },
    )

  fun getOpPackageName(): String = safeReflect(
    directCall = { target.getOpPackageName() }，
    fallbackReflect = { target.callMethod("getOpPackageName", returns = String::class) },
  )
}
```

As you can see, Reflectify first tries to directly call the target, and only falls back to the system-level reflection when the direct call fails. The failure can happen due to various reasons, such as visibility restrictions. But Reflectify will do its best to optimize and handle this process.

That's the basic usage! Let's explore some more advanced features:

#### Customizing Names

You may not always like the original names of the members you want to reflect on. Reflectify allows you to specify the names you want to use in the contract:

```kotlin
var syncLock: Any
  set(value) = contract.set(name = "mSync", value)
  get() = contract.get(name = "mSync")
```

This applies to any signature, such as types or method parameters.

#### Reflecting on Hidden Types

Sometimes, the types of the members you want to reflect on may not be directly accessible, such as the `android.app.LoadedApk` class in the `ContextImpl`  class:

```java
final @NonNull android.app.LoadedApk mPackageInfo;
```

In this case,  you can define a contract for the hidden type and then use it in another contract:

```kotlin
@JvmInline
value class LoadedApk(val target: Any) : Reflectify(targetClass = "android.app.LoadedApk") {
  ...
}
```

```kotlin
@JvmInline
value class ContextImpl(val target: Any) : Reflectify(targetClass = "android.app.ContextImpl") {
  var packageInfo: LoadedApk
    set(value) = contract.set(name = "mPackageInfo", value)
    get() = contract.get(name = "mPackageInfo")

  ...
}
```

Attention! A very cool fact is: in this case, you don't need to manually specify the reflection type for `packageInfo`, as Reflectify will automatically extract the `targetClass` from the `LoadedApk` contract. (This also applies to common list types like `List<*>` and `Array<*>`)

***Of course***, you certainly won't want to define a reflection contract for a result type that only needs to be used simply, as that would be too much trouble. So you can also just use `Any` type and explicitly specify the field type:

```kotlin
@JvmInline
value class ContextImpl(val target: Any) : Reflectify(targetClass = "android.app.ContextImpl") {
  var packageInfo: Any
    set(value) = contract.set(name = "mPackageInfo", type = Type("android.app.LoadedApk"), value)
    get() = contract.get(name = "mPackageInfo", type = Type("android.app.LoadedApk"))

  ...
}
```

However, to make the project more engineering-oriented, you can also define an extension property for convenient use:

```kotlin
inline val C.Companion.LoadedApk
  get() = Type("android.app.LoadedApk")

...

@JvmInline
value class ContextImpl(val target: Any) : Reflectify(targetClass = "android.app.ContextImpl") {
  var packageInfo: Any
    set(value) = contract.set(name = "mPackageInfo", type = C.LoadedApk, value)
    get() = contract.get(name = "mPackageInfo", type = C.LoadedApk)

  ...
}
```

For more information about `C.Companion` and `Type`, you can see the [*Reflecting Mapping Types between Java and Kotlin*](#reflecting-mapping-types-between-java-and-kotlin) section below.

#### Reflecting on Static Members

```java
private static Resources createWindowContextResources(@NonNull ContextImpl windowContextBase) { ... }
```

All definitions under the default `Reflectify` are for instance members. For static members, you need to create a companion object that inherits from `Reflectify.Static`:

```kotlin
@JvmInline
value class ContextImpl(val target: Any) : Reflectify(targetClass = "android.app.ContextImpl") {
  companion object : Reflectify.Static {
    fun createWindowContextResources(contextImpl: ContextImpl) = contract.callVoid(contextImpl)
  }
}
```

#### Defining Dynamic Signatures

Typically, there are some more challenging situations in the real reverse engineering. For example, for obfuscated classes or members, we may not know their actual names and types before running, so we can only use variables as a substitute:

```kotlin
lateinit var LazyUserDataType: String
lateinit var LazyUserDataName: String
lateinit var LazyIdentityType: String

@JvmInline
value class UserData : Reflectify(targetClass = LazyUserDataType) {
  fun toIdentity(): Any = contract.call(
    name = LazyUserDataName,
    returns = LazyIdentityType,
  )
}
```

In this case, Reflectify cannot generate direct call code during the compilation phase, as it cannot infer the referenced dynamic values. Instead, it will fall back to system-level reflection behavior.

**To put it another way**, Reflectify will only generate direct call code if it can evaluate the signatures of the members to be reflected during the compilation phase. Otherwise, it will only use the system-level reflection logic. This includes hard-coded literals, constant references, and some simple property references:

```kotlin
// Hard-code literal
@JvmInline value class Foo : Reflectify(targetClass = "com.example.Foo")

// Const reference
const val FooType = "com.example.Foo"
@JvmInline value class Foo : Reflectify(targetClass = FooType)

// Simple reference
val FooType get() = "com.example.Foo"
val FooType get() {
  return "com.example.Foo"
}
@JvmInline value class Foo : Reflectify(targetClass = FooType)

```

Therefore, all cases outside of these will be treated as **dynamic reflection**.

#### Reflecting Mapping Types between Java and Kotlin

You may know that in the Kotlin world, **`Int`** is equivalent to the primitive type `int` in the Java world, and **`Int?`** is equivalent to the boxed type `java.lang.Integer`. But did you also know that some other types in Kotlin, such as `List`s and `Map`s, are also automatically mapped to existing types in Java?

For example, if you want to reflect on a method like this:

```java
public java.util.Collection getList();
```

Without knowing the detailed mapping rules, you would probably define the contract like this:

```kotlin
fun getList(): java.util.Collection = contract.call()
```

However, you'd want to have the functional programming capabilities that Kotlin provides for collection types. In this case, you might be at a loss.

But in fact, you can just change the type to Kotlin's:

```kotlin
fun getList(): Collection = contract.call()
// Or kotlin.collections.MutableCollection
```

This is because Reflectify follows the same mapping rules. `kotlin.collections.Collection` and `kotlin.collections.MutableCollection` will be treated as `java.util.Collection`.

But at times, you're sure to be very confused: *How do I know which one corresponds to which? This is too complex!*

Fortunately, for this issue, Reflectify provides a value class `Type` to define a safe type mapping relationship, and also provides a series of pre-defined `Type` definitions. This means that when you're feeling confused, you can also accurately reflect the correct members.

For example, in the collection type example above, we can do this:

```kotlin
import com.meowool.reflectify.builtin.C.Companion.Collection

fun getList() = contract.call(returns = C.Collection)
```

That's it! **Just explicitly specify the return type of the method to be reflected, then implicitly ignore the return type defined in the contract.**

This is because the generic of the `Type` value class allows you to get the correct return type! This is type-safe, you don't need to worry about what the types in the Java world look like in the Kotlin world. All the common and easily confusing types in the Java world are pre-defined in `C.Companion`, you just need to type `C.`, and your **IDE** will provide auto-completion for all the available pre-defined types!

## Example

```kotlin
//////////////////////////////////////////////////////////////////////////////////////////
// Usage
//////////////////////////////////////////////////////////////////////////////////////////

@JvmInline
value class User : Reflectify(targetClass = "com.example.data.User") {
  fun setName(value: String) = contract.callVoid(value)

  companion object : Reflectify.Static {
    fun allUser(): List<User> = contract.call("getList")
  }
}

// Integrated with Hookfiy
class UserHooker : Hookify(hookClass = C.User) {
  fun init() = hookConstructor(
    parameters = parametersOf(C.String, C.int, C.int),
    after = { name: String, age: Int, type: Int ->
      debug("User") { "name=$name, age=$age, type=$type" }
    }
  )
}

class UserManagerHooker : Hookify(hookClass = C.UserManager) {
  fun addUser() = hookMethod(
    // Internal support for using reflection contracts as signature types.
    parameters = parametersOf(C.User, C.int),
    returns = C.boolean,
    before = { user: User, type: Int ->
      database.insertUser(user.toEntity(type))
    }
  )
}

//////////////////////////////////////////////////////////////////////////////////////////
// Under the hood
//////////////////////////////////////////////////////////////////////////////////////////

// 1. [KSP] Extends the C.Companion to add a convenient way to use the target class of the contract
inline val C.Companion.User: Type<User> get() = Type("com.example.data.User")

// 2. [KCP] Replace all stub implementations
@JvmInline
value class User(val target: com.example.data.User) : Reflectify(...) {
  fun setName(value: String) = safeReflect(
    id = 12414, // Globally unique ID, generated based on the location hashcode of the source code
    directCall = { target.setName(value) },
    fallbackReflect = { target.callVoid(name = "setName", arg = value) },
  )

  companion object : Reflectify.Static {
    fun allUser(): List<User> = safeReflect(
      id = 23511,
      directCall = { com.example.data.User.getList() },
      fallbackReflect = { com.example.data.User.call<List<com.example.data.User>>(name = "getList") },
    ).map(::User)
  }
}
```

## License

Reflectify is released under the [**Apache License 2.0**](https://www.apache.org/licenses/LICENSE-2.0).

See the **[LICENSE](../LICENSE)** file for more details.
