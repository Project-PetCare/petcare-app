package fi.project.petcare.model.repository

import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.gotrue.SignOutScope
import io.github.jan.supabase.gotrue.auth
import io.github.jan.supabase.gotrue.providers.builtin.Email
import io.github.jan.supabase.gotrue.user.UserInfo
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.put

class AuthRepository(
    private val client: SupabaseClient
) {

    suspend fun signUp(userName: String?, userEmail: String, userPassword: String): Result<UserInfo?> {
        return try {
            val user = client.auth.signUpWith(Email) {
                email = userEmail
                password = userPassword
                userName?.let { data = buildJsonObject { put("full_name", it) } }
            }
            Result.success(user)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun signIn(userEmail: String, userPassword: String): Result<Unit> {
        return try {
            client.auth.signInWith(Email) {
                email = userEmail
                password = userPassword
            }
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun signOut(): Result<Unit> {
        return try {
            client.auth.signOut(SignOutScope.GLOBAL)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}