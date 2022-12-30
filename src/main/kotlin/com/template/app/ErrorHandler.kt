package com.template.app

import arrow.core.Either
import arrow.core.None
import arrow.core.Option
import arrow.core.Some
import com.fasterxml.jackson.core.JsonProcessingException
import com.template.app.common.domain.service.Error
import com.template.app.common.domain.service.InvalidArgumentDomainError
import com.template.app.common.domain.service.NotAuthorizedError
import com.template.app.common.domain.service.NotFoundDomainError
import com.template.app.common.domain.service.RepositoryNotAvailable
import com.template.app.common.domain.service.UnexpectedError
import com.template.app.common.domain.service.UnserializableError
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.plugins.CannotTransformContentToTypeException
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.util.pipeline.*

context(PipelineContext<Unit, ApplicationCall>)
suspend inline fun <reified A : Any?> Either<Error, A>.toResponse(status: HttpStatusCode): Unit =
    when (this) {
        is Either.Left -> handleError(this.value)
        is Either.Right -> when (val response = this.value) {
            is Some<*> -> call.respondNullable(status, response.value)
            is None -> call.respond(status)
            else -> if (response is Unit) call.respond(status) else call.respondNullable(status, response)
        }
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
            is JsonProcessingException -> UnserializableError("Illegal json parameter found")
            else -> when (e) {
                is CannotTransformContentToTypeException -> UnserializableError("Invalid content type")
                else -> UnexpectedError(e.cause.toString())
            }
        }
    }

fun Parameters.getString(param: String) =
    Option.fromNullable(this[param].toString()).toEither { InvalidArgumentDomainError("Parameter <$param> does not exist") }

fun Parameters.getInt(param: String) =
    Option.fromNullable(this[param]?.toIntOrNull()).toEither { InvalidArgumentDomainError("Parameter <$param> does not exist or is invalid") }
