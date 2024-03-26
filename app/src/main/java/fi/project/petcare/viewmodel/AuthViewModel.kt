package fi.project.petcare.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import fi.project.petcare.BuildConfig
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.gotrue.Auth
import io.github.jan.supabase.gotrue.ExternalAuthAction
import io.github.jan.supabase.gotrue.auth
import io.github.jan.supabase.gotrue.providers.builtin.Email
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AuthViewModel: ViewModel() {

    private val apiUrl = BuildConfig.SUPABASE_URL
    private val apiKey = BuildConfig.SUPABASE_KEY


    val supabase = createSupabaseClient(apiUrl, apiKey) {
        install(Auth) {
            host = "fi.project.petcare"
            scheme = "deeplink scheme"
            defaultExternalAuthAction = ExternalAuthAction.CUSTOM_TABS //defaults to EXTERNAL_BROWSER
        }
    }

    fun signUp(userEmail: String, userPassword: String) {
        closeBottomSheet()
        viewModelScope.launch {
            try {
                val user = supabase.auth.signUpWith(Email) {
                    email = userEmail
                    password = userPassword
                }
                Log.i("User registered returns: ", user.toString())
            } catch (e: Exception) {
                Log.e("User registration failed: ", e.toString())
            }
        }
    }

    fun signIn(userEmail: String, userPassword: String) {
        closeBottomSheet()
        viewModelScope.launch {
            try {
                val user = supabase.auth.signInWith(Email) {
                    email = userEmail
                    password = userPassword
                }
                Log.i("User signed in returns: ", user.toString())
            } catch (e: Exception) {
                Log.e("User sign in failed: ", e.toString())
            }
        }
    }

    fun resetPassword(userEmail: String, userPassword: String) {
        closeBottomSheet()
        viewModelScope.launch {
            try {
                val user = supabase.auth.resetPasswordForEmail(userEmail)
                Log.i("User reset password returns: ", user.toString())

                /* TODO: Implement reset password email
                supabase.auth.modifyUser {
                    password = userPassword
                }
                */


            } catch (e: Exception) {
                Log.e("User reset password failed: ", e.toString())
            }
        }
    }

    fun signOut() {
        viewModelScope.launch {
            try {
                val user = supabase.auth.signOut()
                Log.i("User signed out returns: ", user.toString())
            } catch (e: Exception) {
                Log.e("User sign out failed: ", e.toString())
            }
        }
    }

    fun googleSignIn(context: Context) {
        val credentialManager = CredentialManager.create(context)

        // Generate a nonce and hash it with sha-256. Providing a nonce is optional but recommended
        val rawNonce = UUID.randomUUID().toString() // UUID should be sufficient, but can also be any other random string.
        val bytes = rawNonce.toByteArray()
        val md = MessageDigest.getInstance("SHA-256")
        val digest = md.digest(bytes)
        val hashedNonce = digest.fold("") { str, it -> str + "%02x".format(it) } // Hashed nonce to be passed to Google sign-in


        val setServerClientId = BuildConfig.SERVER_CLIENT_ID
        val googleIdOption: GetGoogleIdOption = GetGoogleIdOption.Builder()
            .setFilterByAuthorizedAccounts(false)
            .setServerClientId(setServerClientId)
            .setNonce(hashedNonce) // Provide the nonce if you have one
            .build()

        val request: GetCredentialRequest = GetCredentialRequest.Builder()
            .addCredentialOption(googleIdOption)
            .build()

        viewModelScope.launch {
            try {
                val result = credentialManager.getCredential(
                    request = request,
                    context = context,
                )

                val googleIdTokenCredential = GoogleIdTokenCredential
                    .createFrom(result.credential.data)

                val googleIdToken = googleIdTokenCredential.idToken

                Log.i("Google ID Token", googleIdToken)
                Toast.makeText(context, "You have signed in!", Toast.LENGTH_SHORT).show()

//                Connect to Supabase
//                vModel.supabase.auth.signInWith(IDToken) {
//                    idToken = googleIdToken
//                    provider = Google
//                    nonce = rawNonce
//                }

                // Handle successful sign-in
            } catch (e: GetCredentialException) {
                // Handle GetCredentialException thrown by `credentialManager.getCredential()`
                Log.e("GetCredentialException", e.toString())
            } catch (e: IllegalArgumentException) {
                // Handle IllegalArgumentException thrown by `GoogleIdTokenCredential.createFrom()`
                Log.e("GoogleIdTokenCredential", e.toString())
            } catch (e: GoogleIdTokenParsingException) {
                // Handle GoogleIdTokenParsingException thrown by `GoogleIdTokenCredential.createFrom()`
                Log.e("GoogleIdTokenParsingException", e.toString())
            } catch (e: RestException) {
                // Handle RestException thrown by Supabase
                Log.e("RestException", e.toString())
            } catch (e: Exception) {
                // Handle unknown exceptions
                Log.e("Exception", e.toString())
            }
        }
    }

    fun passkeySignIn(context: Context) {
        Toast.makeText(context, "Coming soon", Toast.LENGTH_SHORT).show()
    }

}
