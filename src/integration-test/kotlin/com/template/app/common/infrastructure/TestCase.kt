package com.template.app.common.infrastructure

import io.kotest.core.spec.style.AnnotationSpec
import io.kotest.koin.KoinExtension
import org.koin.test.KoinTest

abstract class TestCase : AnnotationSpec(), KoinTest {
    override fun extensions() = listOf(
        KoinExtension(listOf(commonDependencies))
    )
}
