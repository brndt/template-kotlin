package com.template.app.common.domain

import arrow.core.Either
import com.template.app.common.domain.service.Error
import kotlin.reflect.KClass

abstract class Subscriber<T : Any> {
    abstract fun subscribedTo(): KClass<T>
    abstract suspend fun invoke(event: T): Either<Error, Unit>
}
