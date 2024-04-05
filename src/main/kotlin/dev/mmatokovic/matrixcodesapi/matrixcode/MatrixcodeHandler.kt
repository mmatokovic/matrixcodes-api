package dev.mmatokovic.matrixcodesapi.matrixcode

import kotlinx.coroutines.flow.Flow
import org.springframework.http.MediaType.APPLICATION_JSON
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.server.*
import org.springframework.web.reactive.function.server.ServerResponse.ok
import java.util.*

@Service
class MatrixcodeHandler(private val matrixcodeRepository: MatrixcodeRepository) {

    suspend fun listMatrixcodes(request: ServerRequest): ServerResponse {
        val matrixcodes: Flow<Matrixcode> = matrixcodeRepository.findAll()
        return ok().contentType(APPLICATION_JSON).bodyAndAwait(matrixcodes);
    }

    suspend fun findMatrixcodeById(request: ServerRequest): ServerResponse {
        val matrixcodeId = request.pathVariable("id")
        return matrixcodeRepository.findById(UUID.fromString(matrixcodeId))?.let {
            ok().contentType(APPLICATION_JSON).bodyValueAndAwait(it)
        } ?: ServerResponse.notFound().buildAndAwait()
    }
}