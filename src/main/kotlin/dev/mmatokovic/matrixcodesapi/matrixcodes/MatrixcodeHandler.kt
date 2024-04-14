package dev.mmatokovic.matrixcodesapi.matrixcodes

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory
import dev.mmatokovic.matrixcodesapi.utils.Validate
import kotlinx.coroutines.flow.Flow
import org.springframework.http.ProblemDetail
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.*
import org.springframework.web.reactive.function.server.ServerResponse.*
import java.net.URI

@Component
class MatrixcodeHandler(val service: MatrixcodeService) {

    private val validate = Validate.configure("spec/openapi.yaml") { _, status, messages ->
        val detail = messages.joinToString(", ")
        ProblemDetail.forStatusAndDetail(status, detail)
    }


    suspend fun listMatrixcodes(request: ServerRequest): ServerResponse {
        val matrixcodes: Flow<MatrixcodeResponse> = service.getMatrixcodes()
        return ok().bodyAndAwait(service.getMatrixcodes())
    }

    suspend fun createMatrixcode(request: ServerRequest): ServerResponse {
        return validate.request(request).awaitBody(MatrixcodeRequest::class.java) {
            val savedMatrixcode = service.saveMatrixcode(it)
            val location: URI = URI.create("/v1/matrixcodes/${savedMatrixcode?.id}")
            created(location).buildAndAwait()
        }
    }

    suspend fun findMatrixcodeById(request: ServerRequest): ServerResponse {
        val matrixcodeId = request.pathVariable("id")
        return service.getMatrixcode(matrixcodeId)?.let { ok().bodyValueAndAwait(it) }
            ?: notFound().buildAndAwait()
    }

    suspend fun updateMatrixcode(request: ServerRequest): ServerResponse {
        val matrixcodeId = request.pathVariable("id")
        val matrixcode = request.awaitBody<MatrixcodeRequest>()
        return service.updateMatrixcode(matrixcodeId, matrixcode)?.let{ noContent().buildAndAwait() }
            ?: notFound().buildAndAwait()
    }

    suspend fun deleteMatrixcode(request: ServerRequest): ServerResponse {
        val matrixcodeId = request.pathVariable("id")
        return service.deleteMatrixcode(matrixcodeId)?.let{ noContent().buildAndAwait() }
            ?: notFound().buildAndAwait()
    }

    suspend fun getResourceFileAsString(request: ServerRequest): ServerResponse {
        val inputStream = this::class.java.getResourceAsStream("/spec/openapi.yaml")
        val objectMapper = ObjectMapper(YAMLFactory())
        return ok().bodyValueAndAwait(objectMapper.readValue(inputStream, Any::class.java))
    }
}