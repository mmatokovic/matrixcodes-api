package dev.mmatokovic.matrixcodesapi.matrixcodes

import kotlinx.coroutines.flow.Flow
import org.springframework.data.r2dbc.repository.Query
import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import java.util.*

interface MatrixcodeRepository : CoroutineCrudRepository<Matrixcode, UUID> {

    fun findByDataContaining(data: String): Flow<Matrixcode>

    @Query("select * from matrixcode where type = :type")
    fun byType(type: String): Flow<Matrixcode>
}