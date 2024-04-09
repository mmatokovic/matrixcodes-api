package dev.mmatokovic.matrixcodesapi.matrixcode

import com.atlassian.oai.validator.OpenApiInteractionValidator
import com.atlassian.oai.validator.model.SimpleRequest
import org.springframework.http.HttpStatus
import org.springframework.http.ProblemDetail
import org.springframework.web.ErrorResponseException
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import reactor.core.publisher.Mono

class MatrixcodeValidator(specUrl: String) {
    private operator fun Regex.contains(text: CharSequence) = this.matches(text)
    private val validator = OpenApiInteractionValidator
        .createFor(specUrl)
        .withBasePathOverride("/v1")
        .build()

    fun validate(request: ServerRequest, body: String? = null): Mono<ServerResponse>? {
        val builder = createSimpleRequestBuilder(request)
        body?.let { builder.withBody(body) }
        val simpleRequest = builder.build()

        val report = validator.validateRequest(simpleRequest)
        return if (report.hasErrors()) {
            val status = status(report.messages[0].key)
            val message = report.messages.firstOrNull()?.message ?: "Unknown error occurred"
            val problemDetail = ProblemDetail.forStatusAndDetail(status, message)

            throw ErrorResponseException(status, problemDetail, null)
        } else null
    }

    fun validateAndAwait(request: ServerRequest, body: String? = null) {
        val builder = createSimpleRequestBuilder(request)
        body?.let { builder.withBody(body) }
        val simpleRequest = builder.build()

        val report = validator.validateRequest(simpleRequest)
        if (report.hasErrors()) {
            val status = status(report.messages[0].key)
            val message = report.messages.firstOrNull()?.message ?: "Unknown error occurred"
            val problemDetail = ProblemDetail.forStatusAndDetail(status, message)

            throw ErrorResponseException(status, problemDetail, null)
        }
    }


    private fun status(key: String) = when (key) {
        in Regex("validation.request.contentType.notAllowed") -> HttpStatus.UNSUPPORTED_MEDIA_TYPE
        in Regex("validation.request.contentType.invalid") -> HttpStatus.UNSUPPORTED_MEDIA_TYPE
        in Regex("validation.request.path.missing") -> HttpStatus.NOT_FOUND
        in Regex("validation.request.accept.invalid") -> HttpStatus.NOT_ACCEPTABLE
        in Regex("validation.request.operation.notAllowed") -> HttpStatus.METHOD_NOT_ALLOWED
        else -> HttpStatus.BAD_REQUEST
    }

    private fun createSimpleRequestBuilder(request: ServerRequest): SimpleRequest.Builder {
        val method = com.atlassian.oai.validator.model.Request.Method.valueOf(request.method().toString())
        val requestBuilder = SimpleRequest.Builder(method, request.path())
        val headerNames = request.headers().asHttpHeaders().keys.asIterable()

        request.queryParams().entries.forEach { requestBuilder.withQueryParam(it.key, it.value) }
        headerNames.forEach { requestBuilder.withHeader(it, request.headers().header(it)) }

        return requestBuilder
    }
}