package fi.project.petcare.model.repository

import fi.project.petcare.model.data.User
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.gotrue.auth
import kotlinx.serialization.json.buildJsonObject
import org.slf4j.MDC.put

class UserRepository(private val client: SupabaseClient) {

    suspend fun updateUser(user: User): Result<Unit> {
        return try {
            client.auth.updateUser {
                email = user.email
                data = buildJsonObject {
                    put("full_name", user.name)
                }
            }
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

}