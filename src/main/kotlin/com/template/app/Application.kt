package com.template.app

import com.template.app.common.infrastructure.SubscribersWorkerPlugin
import com.template.app.common.infrastructure.commonDependencies
import com.template.app.common.infrastructure.commonRoutes
import io.ktor.serialization.jackson.jackson
import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import io.ktor.server.plugins.contentnegotiation.ContentNegotiation
import io.ktor.server.routing.routing
import org.koin.ktor.plugin.Koin

fun main() {
    embeddedServer(Netty, port = 8080) { module() }.start(wait = true)
}

fun Application.module() {
    configureDependencyInjection()
    configureRouting()
    contentNegotiationPlugin()
    install(SubscribersWorkerPlugin)
}

fun Application.configureRouting() {
    routing {
        commonRoutes()
    }
}

fun Application.configureDependencyInjection() {
    install(Koin) {
        modules(commonDependencies)
    }
}

fun Application.contentNegotiationPlugin() {
    install(ContentNegotiation) {
        jackson()
    }
}
