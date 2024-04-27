package fi.project.petcare.model.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

data class PetResponse(
    val pets: List<Pet>? = null,
) {
    @Serializable
    data class Pet(
        val id: String? = null,
        val name: String,
        val species: String,
        val breed: String,
        val gender: String,
        val weight: Double,
        @SerialName("age_months") val ageMonths: Int,
        @SerialName("behavioral_notes") val notes: String? = null,
        @SerialName("img_url") val imgUrl: String? = null,
        @SerialName("microchip_id") val microchipId: Int,
        @SerialName("owner_id") val ownerId: String,
        @SerialName("updated_at") val updatedAt: String? = null,
        @SerialName("created_at") val createdAt: String? = null
    )
}
