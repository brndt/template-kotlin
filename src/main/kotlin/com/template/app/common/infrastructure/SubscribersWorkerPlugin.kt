package com.template.app.common.infrastructure

import com.template.app.common.domain.DomainEvent
import com.template.app.common.domain.Subscriber
import com.template.app.common.infrastructure.service.AsyncEventBus
import io.ktor.server.application.*
import io.ktor.server.application.hooks.*
import kotlinx.coroutines.flow.buffer
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.koin.java.KoinJavaComponent.getKoin

val SubscribersWorkerPlugin = createApplicationPlugin(name = "SubscribersWorkerPlugin") {
    on(MonitoringEvent(ApplicationStarted)) { application ->
        val subscribers = getKoin().getAll<Subscriber<DomainEvent>>()

        AsyncEventBus().eventsCollection
            .buffer()
            .onEach {
                subscribers
                    .filter { subscriber -> subscriber.subscribedTo() === it::class }
                    .forEach { subscriber -> subscriber.invoke(it) }
            }
            .launchIn(application)
    }
}
