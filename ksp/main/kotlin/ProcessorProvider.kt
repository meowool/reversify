package ai.sider.ksp

import codegen.SealedStringsCodegen
import com.google.auto.service.AutoService
import com.google.devtools.ksp.processing.SymbolProcessorEnvironment
import com.google.devtools.ksp.processing.SymbolProcessorProvider
import ai.sider.common.ksp.Processor

@AutoService(SymbolProcessorProvider::class)
class ProcessorProvider : SymbolProcessorProvider {
  override fun create(environment: SymbolProcessorEnvironment) = Processor.Builder()
    .with<SealedStringsCodegen>()
    .build(environment)
}
