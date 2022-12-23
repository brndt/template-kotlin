package com.template.app.common.infrastructure

import com.template.app.ApiTestCase
import io.kotest.matchers.shouldBe
import io.ktor.client.request.get
import io.ktor.client.statement.bodyAsText
import io.ktor.http.HttpStatusCode
import io.ktor.server.testing.testApplication

class CommonRoutesSpec : ApiTestCase() {

    @Test
    fun `health check should return OK`() = testApplication {
        // When
        val actualResponse = client().get("health-check")

        // Then
        actualResponse.status shouldBe HttpStatusCode.OK
        actualResponse.bodyAsText() shouldBe ""
    }
}
