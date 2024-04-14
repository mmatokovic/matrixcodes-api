package dev.mmatokovic.matrixcodesapi.matrixcodes

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.net.URI
import java.sql.Timestamp
import java.time.LocalDateTime
import java.util.*

@Service
class MatrixcodeService(
    @Autowired val repository: MatrixcodeRepository) {

    suspend fun getMatrixcodes(): Flow<MatrixcodeResponse> =
        repository.findAll().map { matrixcode ->
            MatrixcodeResponse(
                id = matrixcode.id,
                data = matrixcode.data,
                url = URI.create("/v1/matrixcodes/${matrixcode.id}"),
                size = matrixcode.size,
                format = matrixcode.format,
                type = matrixcode.type,
                createdAt = matrixcode.createdAt,
                modifiedAt = matrixcode.modifiedAt,
            )
        }

    suspend fun saveMatrixcode(matrixcode: MatrixcodeRequest): Matrixcode? =
        repository.save(
            Matrixcode(
                data = matrixcode.data,
                size = matrixcode.size,
                format = matrixcode.format,
                type = matrixcode.type
            )
        )

    suspend fun getMatrixcode(id: String): MatrixcodeResponse? =
        repository.findById(UUID.fromString(id))?.let { matrixcode ->
            MatrixcodeResponse(
                id = matrixcode.id,
                data = matrixcode.data,
                url = URI.create("/v1/matrixcode/${matrixcode.id}"),
                size = matrixcode.size,
                format = matrixcode.format,
                type = matrixcode.type,
                createdAt = matrixcode.createdAt,
                modifiedAt = matrixcode.modifiedAt
            )
        }

    suspend fun updateMatrixcode(id: String, matrixcode: MatrixcodeRequest): Matrixcode? {
        val existingMatrixcode = repository.findById(UUID.fromString(id))
        if (existingMatrixcode != null) {
            val updatedMatrixcode = existingMatrixcode.copy(
                data = matrixcode.data,
                size = matrixcode.size,
                format = matrixcode.format,
                type = matrixcode.type,
                modifiedAt = Timestamp.valueOf(LocalDateTime.now())
            )
            return repository.save(updatedMatrixcode)
        }
        return null
    }

    suspend fun deleteMatrixcode(id: String): Matrixcode? =
        repository.findById(UUID.fromString(id))
            ?.also { repository.deleteById(UUID.fromString(id)) }
}