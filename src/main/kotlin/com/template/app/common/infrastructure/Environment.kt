package com.template.app.common.infrastructure

import java.lang.System.getenv

data class Environment(
    val app: App = App()
) {
    data class App(
        val env: String = getenv("APP_ENV") ?: "test",
        val port: Int = getenv("APP_PORT")?.toIntOrNull() ?: 8080
    )
}
