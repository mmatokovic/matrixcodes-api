package dev.mmatokovic.matrixcodesapi.matrixcodes

import com.fasterxml.jackson.annotation.JsonProperty
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.Id
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.relational.core.mapping.Table
import java.net.URI
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
    val createdAt: Timestamp? = null,
    @LastModifiedDate
    val modifiedAt: Timestamp? = null
)

data class MatrixcodeRequest(
    val data: String,
    val size: String? = null,
    val format: String? = null,
    val type: String? = null
)

data class MatrixcodeResponse(
    @Id
    val id: UUID?,
    val data: String,
    val url: URI,
    val size: String?,
    val format: String?,
    val type: String?,
    @CreatedDate @JsonProperty("created_at") val createdAt: Timestamp?,
    @LastModifiedDate @JsonProperty("modified_At") val modifiedAt: Timestamp?
)
