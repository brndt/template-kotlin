package com.template.app

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import io.kotest.matchers.shouldBe
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.serialization.jackson.*
import io.ktor.server.testing.*

abstract class ApiTestCase : TestCase() {
    protected fun ApplicationTestBuilder.client() = createClient {
        install(ContentNegotiation) {
            jackson()
        }
    }

    protected fun responsesShouldBeEqual(actualResponse: String, expectedResponse: String) =
        actualResponse.shouldBe(expectedResponse)

    protected fun asJson(value: Any): String =
        jacksonObjectMapper().writeValueAsString(value)
}
