package com.template.app.common.infrastructure

import com.template.app.common.domain.service.Calendar
import com.template.app.common.domain.service.EventBus
import com.template.app.common.infrastructure.service.AsyncEventBus
import com.template.app.common.infrastructure.service.SystemCalendar
import com.template.app.common.infrastructure.service.generateUuid
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val commonDependencies = module {
    singleOf(::generateUuid)
    singleOf(::SystemCalendar) { bind<Calendar>() }
    singleOf(::AsyncEventBus) { bind<EventBus>() }
}
