package dev.mmatokovic.matrixcodesapi

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.Id
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.relational.core.mapping.Table
import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import java.sql.Timestamp
import java.util.*

@SpringBootApplication
class MatrixcodesApiApplication

fun main(args: Array<String>) {
	runApplication<MatrixcodesApiApplication>(*args)
}
@Table("matrixcode")
data class Matrixcode(
	@Id
	val id: UUID?,
	val data: String,
	val url: String?,
	val size: String?,
	val format: String?,
	val type: String?,
	@CreatedDate
	val createdAt: Timestamp?,
	@LastModifiedDate
	val modifiedAt: Timestamp?
)
interface MatrixcodeRepository : CoroutineCrudRepository<Matrixcode, UUID>