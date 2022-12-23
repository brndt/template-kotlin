package com.template.app.common.domain

abstract class DomainEvent {
    abstract fun type(): String
}
