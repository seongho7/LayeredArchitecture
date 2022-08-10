package com.sh.membership

import com.tngtech.archunit.core.importer.ImportOption

import com.tngtech.archunit.junit.AnalyzeClasses
import com.tngtech.archunit.junit.ArchTest
import com.tngtech.archunit.library.Architectures.layeredArchitecture
import com.tngtech.archunit.library.dependencies.SlicesRuleDefinition.slices


@AnalyzeClasses(packagesOf = [ShMembershipApplication::class], importOptions = [ImportOption.DoNotIncludeTests::class])
class ArchUnitTest {

    @ArchTest
    val cycleCheck = slices()
        .matching("com.sh.membership.(*)..")
        .should().beFreeOfCycles()

    @ArchTest
    val layerCheck = layeredArchitecture()
        .layer(LayerName.application).definedBy("..application..")
        .layer(LayerName.domain).definedBy("..domain..")
        .layer(LayerName.presentation).definedBy("..presentation..")
        .layer(LayerName.infrastructure).definedBy("..infrastructure..")
        .layer(LayerName.common).definedBy("..common..")
        .layer(LayerName.translator).definedBy("..translator..")

        .whereLayer(LayerName.application).mayOnlyBeAccessedByLayers(LayerName.presentation)
        .whereLayer(LayerName.infrastructure).mayOnlyBeAccessedByLayers(LayerName.application)
        .whereLayer(LayerName.translator).mayNotBeAccessedByAnyLayer()
        .whereLayer(LayerName.presentation).mayNotBeAccessedByAnyLayer()
}

object LayerName {
    const val application = "Application"
    const val domain = "Domain"
    const val presentation = "Presentation"
    const val infrastructure = "Infrastructure"
    const val common = "Common"
    const val translator = "Translator"
}