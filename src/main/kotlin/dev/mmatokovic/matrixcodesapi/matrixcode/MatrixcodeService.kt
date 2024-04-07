package dev.mmatokovic.matrixcodesapi.matrixcode

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
    @Autowired val matrixcodeRepository: MatrixcodeRepository) {

    suspend fun getMatrixcodes(): Flow<MatrixcodeResponse> =
        matrixcodeRepository.findAll().map { matrixcode ->
            MatrixcodeResponse(
                id = matrixcode.id,
                data = matrixcode.data,
                url = URI.create("/v1/matrixcode/${matrixcode.id}"),
                size = matrixcode.size,
                format = matrixcode.format,
                type = matrixcode.type,
                createdAt = matrixcode.createdAt,
                modifiedAt = matrixcode.modifiedAt,
            )
        }

    suspend fun saveMatrixcode(matrixcode: MatrixcodeRequest): Matrixcode? =
        matrixcodeRepository.save(
            Matrixcode(
                data = matrixcode.data,
                size = matrixcode.size,
                format = matrixcode.format,
                type = matrixcode.type
            )
        )

    suspend fun getMatrixcode(id: String): MatrixcodeResponse? =
        matrixcodeRepository.findById(UUID.fromString(id))?.let { matrixcode ->
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
        val existingMatrixcode = matrixcodeRepository.findById(UUID.fromString(id))
        if (existingMatrixcode != null) {
            val updatedMatrixcode = existingMatrixcode.copy(
                data = matrixcode.data,
                size = matrixcode.size,
                format = matrixcode.format,
                type = matrixcode.type,
                modifiedAt = Timestamp.valueOf(LocalDateTime.now())
            )
            return matrixcodeRepository.save(updatedMatrixcode)
        }
        return null
    }


    suspend fun deleteMatrixcode(id: String) =
        matrixcodeRepository.deleteById(UUID.fromString(id))
}