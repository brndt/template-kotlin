package com.template.app.common.domain.service

fun interface GenerateId {
    operator fun invoke(): String
}
