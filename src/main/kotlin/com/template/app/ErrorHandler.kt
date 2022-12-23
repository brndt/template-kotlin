package com.template.app

import arrow.core.Either
import com.fasterxml.jackson.module.kotlin.MissingKotlinParameterException
import com.template.app.common.domain.service.Error
import com.template.app.common.domain.service.InvalidArgumentDomainError
import com.template.app.common.domain.service.NotAuthorizedError
import com.template.app.common.domain.service.NotFoundDomainError
import com.template.app.common.domain.service.RepositoryNotAvailable
import com.template.app.common.domain.service.UnexpectedError
import com.template.app.common.domain.service.UnserializableError
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.util.pipeline.*

context(PipelineContext<Unit, ApplicationCall>)
suspend inline fun <reified A : Any> Either<Error, A>.toResponse(status: HttpStatusCode): Unit =
    when (this) {
        is Either.Left -> handleError(this.value)
        is Either.Right -> call.respond(status, this.value)
    }

suspend fun PipelineContext<Unit, ApplicationCall>.handleError(error: Error): Unit =
    when (error) {
        is InvalidArgumentDomainError -> call.respond(HttpStatusCode.BadRequest, error.toPrimitives())
        is NotAuthorizedError -> call.respond(HttpStatusCode.Forbidden, error.toPrimitives())
        is NotFoundDomainError -> call.respond(HttpStatusCode.NotFound, error.toPrimitives())
        is UnserializableError -> call.respond(HttpStatusCode.BadRequest, error.toPrimitives())
        is UnexpectedError -> call.respond(HttpStatusCode.InternalServerError, error.toPrimitives())
        is RepositoryNotAvailable -> call.respond(HttpStatusCode.InternalServerError, error.toPrimitives())
    }

suspend inline fun <reified T : Any> PipelineContext<Unit, ApplicationCall>.receive(): Either<Error, T> =
    Either.catch { call.receive<T>() }.mapLeft { e ->
        when (e.cause) {
            is MissingKotlinParameterException -> UnserializableError("Cannot serialize the request because it's missing mandatory parameter")
            else -> UnexpectedError(e.message ?: "Unexpected error happened")
        }
    }
