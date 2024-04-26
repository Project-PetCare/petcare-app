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

val demoUser: User = User(
    id = "123e4567-e89b-12d3-a456-426614174000",
    name = "John Demo",
    email = "john@demo.com"
)
