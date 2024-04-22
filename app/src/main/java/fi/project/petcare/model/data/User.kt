package fi.project.petcare.model.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

// Data transfer object
@Serializable
data class User(
    val id: String,
    @SerialName("full_name") val name: String?,
    val email: String
)
