package com.template.app.common.domain.service

sealed class Error(open val code: String, open val message: String) {
    fun toPrimitives() = mapOf("code" to code, "message" to message)
}

open class InvalidArgumentDomainError(override val message: String) :
    Error("invalid_argument_code", message)

open class NotFoundDomainError(override val message: String) : Error("not_found_error", message)

class NotAuthorizedError : Error("not_authorized_code", "You are not authorized")

class UnserializableError(override val message: String) : Error("unserializable_code", message)

class UnexpectedError(override val message: String) : Error("unexpected_code", message)

class RepositoryNotAvailable(override val message: String) : Error("unexpected_code", message)
