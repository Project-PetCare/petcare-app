package fi.project.petcare.model.data

import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

enum class AuthMode {
    LOGIN,
    REGISTER
}

@Serializable
data class User(
    val id: String,
    val name: String,
    val email: String,
    val password: String?,
    @SerialName("created_at") val createdAt: LocalDateTime,
    @SerialName("updated_at") val updatedAt: LocalDateTime,
    val isAuthenticated: Boolean,
)
