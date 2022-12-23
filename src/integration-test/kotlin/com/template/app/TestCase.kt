package com.template.app

import com.template.app.common.infrastructure.commonDependencies
import io.kotest.core.spec.style.AnnotationSpec
import io.kotest.koin.KoinExtension
import org.koin.test.KoinTest

abstract class TestCase : AnnotationSpec(), KoinTest {
    override fun extensions() = listOf(
        KoinExtension(listOf(commonDependencies))
    )
}
