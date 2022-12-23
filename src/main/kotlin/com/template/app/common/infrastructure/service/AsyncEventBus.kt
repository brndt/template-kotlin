package com.template.app.common.infrastructure.service

import com.template.app.common.domain.DomainEvent
import com.template.app.common.domain.service.EventBus
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow

class AsyncEventBus : EventBus {
    private val events = MutableSharedFlow<DomainEvent>()
    val eventsCollection = events.asSharedFlow()

    override suspend fun publish(eventsToPublish: List<DomainEvent>) {
        eventsToPublish.forEach { events.emit(it) }
    }
}
