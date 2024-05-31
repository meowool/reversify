# Reflectify

A high-performance reflection framework that leverages the **Kotlin compiler** and **Kotlin Symbol Processing (KSP)** to provide a seamless and efficient way to work with reflection in your Kotlin projects.

## Features

- **Contract-Based Reflection**: Traditional reflection code can be verbose and difficult to manage. Reflectify solves this by allowing you to define a contract class for the class you want to reflect on. This contract serves as a standardized way to interact with the reflected class, improving readability and maintainability.
- **High Performance**: Reflection is notoriously slow in Java and Kotlin world. Reflectify is designed to address this issue. In many cases, it does not actually execute reflection at runtime. Instead, it replaces the contract class with direct method calls during the compilation process, resulting in zero-overhead performance.

## Tutorial

Reflectify allows you to define a **value class** that represents a contract of the class you want to reflect on, and Reflectify will generate the necessary code to handle the reflection behavior.

------

Let's take a look at a simple example:

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

By calling the `contract.` methods provided by the contract, Reflectify will automatically replace them with the appropriate reflection logic during the compilation process. Here's a simplified pseudo-code of how it might work:

```kotlin
@JvmInline
value class ContextImpl(private target: android.app.ContextImpl) : Reflectify(...) {
  var mSync: Any
    set(value) = safeReflect(
      directCall = { target.mSync = value }，
      fallbackReflect = { target.setField("mSync", type = Object::class, value = value) },
    )
    get() = safeReflect(
      directCall = { target.mSync }，
      fallbackReflect = { target.getField("mSync", type = Object::class) },
    )

  fun getOpPackageName(): String = safeReflect(
    directCall = { target.getOpPackageName() }，
    fallbackReflect = { target.callMethod("getOpPackageName", returns = String::class) },
  )
}
```

As you can see, Reflectify first tries to directly call the target, and only falls back to the system-level reflection when the direct call fails. The failure can happen due to various reasons, such as visibility restrictions. But Reflectify will do its best to optimize and handle this process.

That's the basic usage! Let's explore some more advanced features:

### Customizing Names

You may not always like the original names of the members you want to reflect on. Reflectify allows you to specify the names you want to use in the contract:

```kotlin
var syncLock: Any
  set(value) = contract.set(name = "mSync", value)
  get() = contract.get(name = "mSync")
```

This applies to any signature, such as types or method parameters.

### Reflecting on Hidden Types

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

### Reflecting on Static Members

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

### Defining Dynamic Signatures

Typically, there are some more challenging situations in the real reverse engineering. For example, for obfuscated members, we may not know their actual names and types before running, so we can only use variables as a substitute:

```kotlin
@JvmInline
value class UserData : Reflectify(targetClass = "...") {
  fun toIdentity(): Any = contract.call(
    name = InferPlaceholder.UserData.name,
    returns = C.Identity,
  )
}
```

In this case, Reflectify cannot generate direct call code during the compilation phase, as it cannot infer the name and return type. Instead, it will fall back to system-level reflection behavior.

However, there are exceptions where Reflectify will still generate direct call code if the definition points to a constant instead of a variable.

## Example

```kotlin
////////////////////////////////////////////////////////////////////////////////
// Usage
////////////////////////////////////////////////////////////////////////////////

@JvmInline
value class User : Reflectify(targetClass = "com.example.data.User") {
  fun setName(value: String) = contract.callVoid(value)

  companion object : Reflectify.Static {
    fun allUser(): List<User> = contract.call("getList")
  }
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

////////////////////////////////////////////////////////////////////////////////
// Under the hood
////////////////////////////////////////////////////////////////////////////////

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

