package dev.mmatokovic.matrixcodesapi.matrixcode

import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import org.springframework.http.MediaType.APPLICATION_JSON
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.*
import org.springframework.web.reactive.function.server.ServerResponse.created
import org.springframework.web.reactive.function.server.ServerResponse.ok
import java.net.URI

@Component
class MatrixcodeHandler(
    private val matrixcodeService: MatrixcodeService) {

    @FlowPreview
    suspend fun listMatrixcodes(request: ServerRequest): ServerResponse {
        val matrixcodes: Flow<Matrixcode> = matrixcodeService.getMatrixcodes()
        return ok()
            .contentType(APPLICATION_JSON)
            .bodyAndAwait(matrixcodes)
    }

    suspend fun createMatrixcode(request: ServerRequest): ServerResponse {
        val matrixcode = request.awaitBody<Matrixcode>()
        val savedMatrixcode = matrixcodeService.saveMatrixcode(matrixcode)
        val location: URI = URI.create("/matrixcode/${savedMatrixcode?.id}")
        return created(location)
            .buildAndAwait()
    }

    suspend fun findMatrixcodeById(request: ServerRequest): ServerResponse {
        val matrixcodeId = request.pathVariable("id")
        return matrixcodeService.getMatrixcode(matrixcodeId)?.let {
            ok()
                .contentType(APPLICATION_JSON)
                .bodyValueAndAwait(it)
        } ?: ServerResponse.notFound()
                .buildAndAwait()
    }
}