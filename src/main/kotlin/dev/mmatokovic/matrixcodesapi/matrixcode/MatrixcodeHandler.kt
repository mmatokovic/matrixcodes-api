package dev.mmatokovic.matrixcodesapi.matrixcode

import kotlinx.coroutines.flow.Flow
import org.springframework.http.MediaType.APPLICATION_JSON
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.*
import org.springframework.web.reactive.function.server.ServerResponse.*
import java.net.URI

@Component
class MatrixcodeHandler(
    private val matrixcodeService: MatrixcodeService) {

    private val validator = MatrixcodeValidator("spec/openapi.yaml")

    suspend fun listMatrixcodes(request: ServerRequest): ServerResponse {
        val matrixcodes: Flow<MatrixcodeResponse> = matrixcodeService.getMatrixcodes()
        return ok().contentType(APPLICATION_JSON).bodyAndAwait(matrixcodes)
    }

    suspend fun createMatrixcode(request: ServerRequest): ServerResponse {
        validator.validate(request)
        val matrixcode = request.awaitBody<MatrixcodeRequest>()
        val savedMatrixcode = matrixcodeService.saveMatrixcode(matrixcode)
        val location: URI = URI.create("/v1/matrixcode/${savedMatrixcode?.id}")
        return created(location).buildAndAwait()
    }

    suspend fun findMatrixcodeById(request: ServerRequest): ServerResponse {
        val matrixcodeId = request.pathVariable("id")
        return matrixcodeService.getMatrixcode(matrixcodeId)?.let { ok().contentType(APPLICATION_JSON).bodyValueAndAwait(it) }
            ?: notFound().buildAndAwait()
    }

    suspend fun updateMatrixcode(request: ServerRequest): ServerResponse {
        val matrixcodeId = request.pathVariable("id")
        val matrixcode = request.awaitBody<MatrixcodeRequest>()
        return matrixcodeService.updateMatrixcode(matrixcodeId, matrixcode)?.let{ noContent().buildAndAwait() }
            ?: notFound().buildAndAwait()
    }

    suspend fun deleteMatrixcode(request: ServerRequest): ServerResponse {
        val matrixcodeId = request.pathVariable("id")
        return matrixcodeService.deleteMatrixcode(matrixcodeId)?.let{ noContent().buildAndAwait() }
            ?: notFound().buildAndAwait()
    }
}