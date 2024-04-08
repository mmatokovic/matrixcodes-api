package dev.mmatokovic.matrixcodesapi.matrixcode

import com.atlassian.oai.validator.OpenApiInteractionValidator
import com.atlassian.oai.validator.model.SimpleRequest
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.server.ServerWebInputException
import reactor.core.publisher.Mono

class MatrixcodeValidator(
    swaggerJsonPath: String) {

    private val swaggerValidator = OpenApiInteractionValidator
        .createForSpecificationUrl(swaggerJsonPath)
        .build()

    fun validate(request: ServerRequest, body: String? = null): Mono<ServerResponse>? {
        val builder = createSimpleRequestBuilder(request)
        body?.let { builder.withBody(it) }
        val simpleRequest = builder.build()

        val report = swaggerValidator.validateRequest(simpleRequest)
        return if (report.hasErrors()) {
            Mono.error(ServerWebInputException(report.toString()))
        } else null
    }

    private fun createSimpleRequestBuilder(request: ServerRequest): SimpleRequest.Builder {
        val method = com.atlassian.oai.validator.model.Request.Method.valueOf(request.method().toString())
        val requestBuilder = SimpleRequest.Builder(method, request.path())
        request.queryParams().forEach { (key, value) -> requestBuilder.withQueryParam(key, value) }
        request.headers().asHttpHeaders().forEach { (key, value) -> requestBuilder.withHeader(key, value) }
        return requestBuilder
    }
}