package com.template.app.common.infrastructure

import java.lang.System.getenv

data class Env(
    val mongo: Mongo = Mongo()
) {
    data class App(
        val env: String = getenv("APP_ENV") ?: "test"
    )

    data class Mongo(
        val host: String = getenv("MONGO_HOST") ?: "localhost",
        val port: Int = getenv("MONGO_TCP_27017")?.toIntOrNull() ?: 27017
    )
}
