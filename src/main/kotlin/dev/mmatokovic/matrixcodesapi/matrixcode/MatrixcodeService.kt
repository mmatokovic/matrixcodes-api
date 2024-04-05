package dev.mmatokovic.matrixcodesapi.matrixcode

import kotlinx.coroutines.flow.Flow
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.*

@Service
class MatrixcodeService(
    @Autowired val matrixcodeRepository: MatrixcodeRepository) {

    suspend fun getMatrixcodes(): Flow<Matrixcode> =
        matrixcodeRepository.findAll()

    suspend fun saveMatrixcode(matrixcode: Matrixcode): Matrixcode? =
        matrixcodeRepository.save(matrixcode)

    suspend fun getMatrixcode(id: String): Matrixcode? =
        matrixcodeRepository.findById(UUID.fromString(id))
    //suspend fun updateMatrixcode(matrixcode: Matrixcode):

    //suspend fun deleteMatrixcode(matrixcode: Matrixcode):
}