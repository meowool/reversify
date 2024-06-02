package codegen

import ai.sider.common.ksp.Codegen
import ai.sider.common.ksp.addInvisibleSuppress
import ai.sider.common.ksp.findAnnotation
import ai.sider.common.ksp.findArgument
import ai.sider.common.ksp.findStringArgument
import ai.sider.common.ksp.validateAnnotation
import ai.sider.common.ksp.write
import com.meowool.reflectify.common.castOrNull
import ai.sider.ksp.PackageNames
import com.google.devtools.ksp.symbol.KSClassDeclaration
import com.google.devtools.ksp.symbol.Modifier
import com.squareup.kotlinpoet.AnnotationSpec
import com.squareup.kotlinpoet.FileSpec
import com.squareup.kotlinpoet.FunSpec
import com.squareup.kotlinpoet.PropertySpec
import com.squareup.kotlinpoet.buildCodeBlock
import com.squareup.kotlinpoet.ksp.toClassName

private val STRINGS_FACTORY = PackageNames.commonAndroid.classFor("StringsFactory")
private val SEALED_ANNOTATION = PackageNames.commonAndroid.classFor("SealedStrings")
private val LOCALIZED_STRINGS_ANNOTATION = PackageNames.commonAndroid.classFor("LocalizedStrings")
private val LANGUAGE_TAGS = PackageNames.commonAndroid.classFor("LanguageTags")

class SealedStringsCodegen : Codegen<List<KSClassDeclaration>>(ProcessStage.Incremental) {
  override fun collectSymbols(): List<KSClassDeclaration> {
    val result = context.getSymbolsWithAnnotation(SEALED_ANNOTATION.canonicalName)
      .filterIsInstance<KSClassDeclaration>()
      .validateAnnotation(SEALED_ANNOTATION) { Modifier.SEALED in it.modifiers }
      .toList()

    // @SealedStrings(name = "???") should be unique
    result.map { it.sealedStringsName() }
      .groupingBy { it }
      .eachCount()
      .forEach { (name, count) ->
        if (count > 1) context.error(
          "Duplicated sealed strings name: $name. " +
            "Your current module has other classes that have used the same name argument."
        )
      }

    return result
  }

  override fun incrementalStage(collected: List<KSClassDeclaration>) = collected.forEach {
    // Generate a getter for each subclass marked with the @LocalizedStrings annotation to
    // extend the StringsFactory.Companion object
    // --
    // @file:Suppress("INVISIBLE_MEMBER", "INVISIBLE_REFERENCE")
    //
    // val StringsFactory.Companion.home: Strings get() {
    //   val value = map.get(100099248184)
    //   if (value == null) {
    //     val strings = when {
    //       LanguageTags.isMatches(configuration, English) -> Strings.English()
    //       LanguageTags.isMatches(configuration, SimplifiedChinese) -> Strings.SimplifiedChinese()
    //       LanguageTags.isMatches(configuration, TraditionalChinese) -> Strings.Traditional()
    //       else -> Strings.English()
    //     }
    //     map.put(100099248184, strings)
    //     return strings
    //   }
    //   return value as Strings
    // }
    val allLocalizedStrings = it.getSealedSubclasses().onEach { subclass ->
      require(subclass.findAnnotation(LOCALIZED_STRINGS_ANNOTATION) != null) {
        "The subclass '${subclass.simpleName}' of the sealed class '${it.simpleName}' must be annotated with '@LocalizedStrings'."
      }
    }
    val defaultLocalizedStrings = requireNotNull(allLocalizedStrings.singleOrNull { subclass ->
      subclass.findAnnotation(LOCALIZED_STRINGS_ANNOTATION)!!
        .findArgument("default")?.value
        .castOrNull<Boolean>() == true
    }) {
      "The subclass of the sealed class '${it.simpleName}' must have one and only one subclass annotated with '@LocalizedStrings(default = true)'."
    }

    val name = it.sealedStringsName()
    val hash = name.hashCode()
    val getter = FunSpec.getterBuilder().addCode(
      buildCodeBlock {
        addStatement("val·value·=·map.get(%L)", hash)
        beginControlFlow("if·(value·==·null)")
        addStatement("val·strings·=·when·{")
        indent()

        for (subclass in allLocalizedStrings) {
          addStatement(
            "%T.isMatches(configuration,·%S)·->·%T()",
            LANGUAGE_TAGS,
            subclass.findAnnotation(LOCALIZED_STRINGS_ANNOTATION)!!.findStringArgument("languageTag")!!,
            subclass.toClassName(),
          )
        }

        addStatement("else·->·%T()", defaultLocalizedStrings.toClassName())

        unindent()
        addStatement("}")
        addStatement("map.put(%L,·strings)", hash)
        addStatement("return·strings")
        endControlFlow()

        addStatement("return·value·as·%T", it.toClassName())
      }
    )

    val property = PropertySpec.builder(name, it.toClassName())
      .receiver(STRINGS_FACTORY)
      .getter(getter.build())
      .build()

    val annotation = AnnotationSpec.builder(Suppress::class)
      .addInvisibleSuppress()
      .build()

    FileSpec.builder(it.packageName.asString(), "${it.simpleName.asString()}_i18n")
      .addAnnotation(annotation)
      .addProperty(property)
      .build()
      .write(it)
  }

  private fun KSClassDeclaration.sealedStringsName() = findAnnotation(SEALED_ANNOTATION)
    .findStringArgument("name") ?: context.moduleName
}
