package fi.project.petcare.model.repository

import fi.project.petcare.model.data.PetResponse
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.postgrest.from

class PetRepository(private val client: SupabaseClient) {

    suspend fun getPets(userId: String): Result<List<PetResponse.Pet>> {
        return try {
            val pets = client.from("pets").select {
                filter {
                    PetResponse.Pet::ownerId eq userId
                }
            }.decodeList<PetResponse.Pet>()
            Result.success(pets)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getPetById(petId: String): Result<PetResponse.Pet> {
        return try {
            val pet = client.from("pets").select {
                filter {
                    PetResponse.Pet::id eq petId
                }
            }.decodeSingle<PetResponse.Pet>()
            Result.success(pet)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun upsertPet(pet: PetResponse.Pet): Result<Unit> {
        return try {
            client.from("pets").upsert(
                value = pet,
                onConflict = "microchip_id"
            )
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}