package com.template.app

import io.kotest.core.spec.style.AnnotationSpec
import io.kotest.matchers.shouldBe

class ExampleSpec : AnnotationSpec() {

    @Test
    fun `strings should be equals`() {
        // Given
        val exampleText = "Hello world"

        // Then
        exampleText shouldBe "Hello world"
    }
}
