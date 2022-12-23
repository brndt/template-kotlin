package com.template.app.common.infrastructure.service

import com.template.app.common.domain.service.Calendar
import java.util.*

class SystemCalendar : Calendar {
    override fun now(): String = Date().toString()
}
