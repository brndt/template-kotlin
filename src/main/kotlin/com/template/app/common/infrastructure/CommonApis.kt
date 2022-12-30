package com.template.app.common.infrastructure

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.commonApis() {
    get("/health-check") {
        call.respond(HttpStatusCode.OK)
    }
}
