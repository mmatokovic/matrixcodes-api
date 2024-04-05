package dev.mmatokovic.matrixcodesapi.matrixcode

import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.Id
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.relational.core.mapping.Table
import java.sql.Timestamp
import java.util.*

@Table("matrixcode")
data class Matrixcode(
    @Id
    val id: UUID? = null,
    val data: String,
    val size: String?,
    val format: String?,
    val type: String?,
    @CreatedDate
    val createdAt: Timestamp?,
    @LastModifiedDate
    val modifiedAt: Timestamp?
)