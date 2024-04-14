package dev.mmatokovic.matrixcodesapi.utils

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.springframework.http.HttpStatus
import org.springframework.web.reactive.function.BodyExtractors
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.awaitBodyOrNull
import reactor.core.publisher.Mono

operator fun Regex.contains(text: CharSequence): Boolean = this.matches(text)

data class ValidationError(val request: ServerRequest, val code: Int, val message: String)

typealias ErrorHandler<T> = (request: ServerRequest, status: HttpStatus, List<String>) -> T

typealias ObjectMapperFactory = () -> ObjectMapper

class Validate<out T> internal constructor(
    swaggerJsonPath: String,
    errorHandler: ErrorHandler<T>,
    private val objectMapperFactory: ObjectMapperFactory) {

    companion object Instance {
        private val defaultErrorHandler: ErrorHandler<ValidationError> =
            { request, status, messages ->
                ValidationError(request, status.value(), messages[0]) }

        private val defaultObjectMapperFactory: ObjectMapperFactory = { jacksonObjectMapper() }

        fun configure(specUrl: String) =
            configure(specUrl, defaultErrorHandler)

        fun <T> configure(specUrl: String, errorHandler: ErrorHandler<T>) =
            configure(specUrl, defaultObjectMapperFactory, errorHandler)

        fun <T> configure(specUrl: String, objectMapperFactory: ObjectMapperFactory, errorHandler: ErrorHandler<T>) =
            Validate(specUrl, errorHandler, objectMapperFactory)
    }

    private val validator = Validator(swaggerJsonPath, errorHandler)

    fun request(request: ServerRequest) = Request(request, objectMapperFactory)

    fun request(request: ServerRequest, handler: () -> Mono<ServerResponse>) = validator.validate(request) ?: handler()

    suspend fun requestAndAwait(request: ServerRequest, handler: suspend () -> ServerResponse): ServerResponse =
        validator.validateAndAwait(request) ?: handler()

    inner class Request(val request: ServerRequest, val objectMapperFactory: ObjectMapperFactory) {

        fun <T> withBody(bodyType: Class<T>, handler: (T) -> Mono<ServerResponse>): Mono<ServerResponse> {
            return withBody(bodyType, readJsonValue(bodyType), handler)
        }

        fun <T> withBody(bodyType: Class<T>, readValue: (String) -> T, handler: (T) -> Mono<ServerResponse>): Mono<ServerResponse> {
            return BodyValidator(request, bodyType, objectMapperFactory).validate(handler, readValue)
        }

        inline fun <reified T> withBody(noinline handler: (T) -> Mono<ServerResponse>): Mono<ServerResponse> =
            this.withBody(T::class.java, handler = handler)

        suspend fun <T> awaitBody(bodyType: Class<T>, readValue: (String) -> T = readJsonValue(bodyType), handler: suspend (T) -> ServerResponse): ServerResponse {
            return BodyValidator(request, bodyType, objectMapperFactory).validateAndAwait(handler, readValue)
        }

        private fun <T> readJsonValue(bodyType: Class<T>): (String) -> T = { json ->
            objectMapperFactory().readValue(json, bodyType)
        }

    }

    inner class BodyValidator<T>(val request: ServerRequest, val bodyType: Class<T>, val objectMapperFactory: ObjectMapperFactory) {

        fun validate(handler: (T) -> Mono<ServerResponse>, readValue: (String) -> T): Mono<ServerResponse> {
            val json = request.body(BodyExtractors.toMono(String::class.java)).switchIfEmpty(Mono.just(""))
            return json.flatMap { validator.validate(request, it) ?: handler(readValue(it)) }
        }

        suspend fun validateAndAwait(handler: suspend (T) -> ServerResponse, readValue: (String) -> T): ServerResponse {
            val json = request.awaitBodyOrNull() ?: ""
            return json.let { validator.validateAndAwait(request, it) ?: handler(readValue(it)) }
        }

    }
}