package com.template.app.common.infrastructure.service

import com.template.app.common.domain.service.GenerateId
import java.util.*

fun generateUuid(): GenerateId = GenerateId { UUID.randomUUID().toString() }
