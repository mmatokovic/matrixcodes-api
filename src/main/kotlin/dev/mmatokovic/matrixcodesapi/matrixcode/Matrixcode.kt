package dev.mmatokovic.matrixcodesapi.matrixcode

import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.Id
import org.springframework.data.annotation.LastModifiedDate
import java.sql.Timestamp
import java.util.UUID

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
