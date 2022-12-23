package com.template.app.common.domain.service

import com.template.app.common.domain.DomainEvent

interface EventBus {
    suspend fun publish(eventsToPublish: List<DomainEvent>): Unit
}
