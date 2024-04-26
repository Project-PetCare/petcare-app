package fi.project.petcare.model.data

import fi.project.petcare.BuildConfig
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.compose.auth.ComposeAuth
import io.github.jan.supabase.compose.auth.googleNativeLogin
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.gotrue.Auth
import io.github.jan.supabase.postgrest.Postgrest

class SupabaseConfig {
    companion object {
        val supabaseUrl = BuildConfig.SUPABASE_URL
        val supabaseKey = BuildConfig.SUPABASE_KEY
    }
}

object SupabaseClientFactory {
    private var instance: SupabaseClient? = null

    fun getInstance(): SupabaseClient {
        if (instance == null) {
            instance = createSupabaseClient(
                SupabaseConfig.supabaseUrl, SupabaseConfig.supabaseKey
            ) {
                install(Auth)
                install(Postgrest)
                install(ComposeAuth) {
                    googleNativeLogin(serverClientId = BuildConfig.SERVER_CLIENT_ID)
                }
            }
        }
        return instance!!
    }
}