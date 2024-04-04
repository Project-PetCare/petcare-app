package fi.project.petcare.viewmodel

import android.content.Context
import androidx.credentials.CredentialManager
import androidx.credentials.GetCredentialRequest
import androidx.credentials.exceptions.GetCredentialException
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.android.libraries.identity.googleid.GoogleIdTokenParsingException
import android.util.Log
import androidx.credentials.CreatePasswordRequest
import androidx.credentials.exceptions.GetCredentialCancellationException
import androidx.credentials.exceptions.NoCredentialException
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import fi.project.petcare.BuildConfig
import fi.project.petcare.utils.buildGoogleSignInRequest
import fi.project.petcare.utils.generateHashedNonce
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.exceptions.BadRequestRestException
import io.github.jan.supabase.gotrue.Auth
import io.github.jan.supabase.gotrue.ExternalAuthAction
import io.github.jan.supabase.gotrue.auth
import io.github.jan.supabase.gotrue.providers.builtin.Email
import io.github.jan.supabase.exceptions.RestException
import io.github.jan.supabase.gotrue.providers.Google
import io.github.jan.supabase.gotrue.providers.builtin.IDToken
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

sealed class AuthUiState {
    object Unauthenticated : AuthUiState()
    object Authenticated : AuthUiState()
    object Verifying : AuthUiState()
    object Loading : AuthUiState()
    data class Error(val messageId: Int?, val message: String) : AuthUiState()
}

class AuthViewModel: ViewModel() {
    private val _authUiState = MutableStateFlow<AuthUiState>(AuthUiState.Unauthenticated)
    val authUiState: StateFlow<AuthUiState> = _authUiState

    private fun clearErrorStateWithDelay(delayMillis: Long? = 2750) {
        viewModelScope.launch {
            delayMillis?.let { delay(it) }
            if (_authUiState.value is AuthUiState.Error) {
                _authUiState.value = AuthUiState.Unauthenticated
            }
        }

    }

    private val apiUrl = BuildConfig.SUPABASE_URL
    private val apiKey = BuildConfig.SUPABASE_KEY

    private val supabase = createSupabaseClient(apiUrl, apiKey) {
        install(Auth) {
            host = "fi.project.petcare"
            scheme = "deeplink scheme"
            defaultExternalAuthAction = ExternalAuthAction.CUSTOM_TABS //defaults to EXTERNAL_BROWSER
        }
    }

    fun signUp(userEmail: String, userPassword: String) {
        _authUiState.value = AuthUiState.Loading
        val createPasswordRequest = CreatePasswordRequest(id = userEmail, password = userPassword)
        viewModelScope.launch {
            try {
                val user = supabase.auth.signUpWith(Email) {
                    email = userEmail
                    password = userPassword
                }
                Log.i("User registered returns: ", user.toString())
//                _authUiState.value = AuthUiState.Verifying
                _authUiState.value = AuthUiState.Authenticated

            } catch (e: Exception) {
                Log.e("User registration failed: ", e.toString())
                _authUiState.value = AuthUiState.Error(
                    messageId = 1,
                    message = "Something went wrong. Please try again later."
                )
                clearErrorStateWithDelay()
            }
        }
    }

//    private var attempts = 3 // Maximum number of failed attempts. May be changed in the future

    fun signIn(userEmail: String, userPassword: String) {
        _authUiState.value = AuthUiState.Loading
        viewModelScope.launch {
            try {
                val user = supabase.auth.signInWith(Email) {
                    email = userEmail
                    password = userPassword
                }
                Log.i("User signed in returns: ", user.toString())
                _authUiState.value = AuthUiState.Authenticated
            } catch (e: BadRequestRestException) {
                if (e.message?.contains("Email not confirmed") == true) {
                    _authUiState.value = AuthUiState.Error(
                        messageId = 2,
                        message = "Please verify your email address."
                    )
                    clearErrorStateWithDelay()

                } else if (e.message?.contains("Invalid login credentials") == true) {
                    _authUiState.value = AuthUiState.Error(
                        messageId = 2,
                        message = "Email or password is incorrect."
                    )
                    clearErrorStateWithDelay()

                }
                Log.e("Exception: ", e.toString())

                /* TODO: Implement login attempts
                attempts--
                if (attempts == 0) {
                    _authUiState.value = AuthUiState.Error("Please try again later.")
                    return@launch
                }
                 */
            } catch (e: Exception) {
                Log.e("User sign in failed: ", e.toString())
                _authUiState.value = AuthUiState.Error(
                    messageId = 2,
                    message = e.message.toString()
                )
                clearErrorStateWithDelay()
            }
        }
    }

    fun updateUser(userEmail: String?, userPassword: String?, redirectUrl: String?) {
        viewModelScope.launch {
            try {
                userEmail?.let { email ->
                    supabase.auth.resetPasswordForEmail(email)
                }
                userPassword?.let { newPassword ->
                    supabase.auth.modifyUser {
                        password = newPassword
                    }
                }
            } catch (e: Exception) {
                _authUiState.value = AuthUiState.Error(
                    messageId = 3,
                    message = "Something went wrong. Please try again later."
                )
                Log.e("User reset password failed: ", e.toString())
            }
        }
    }

    fun signOut() {
        _authUiState.value = AuthUiState.Loading
        viewModelScope.launch {
            try {
                val user = supabase.auth.signOut()
                Log.i("User signed out returns: ", user.toString())
                _authUiState.value = AuthUiState.Unauthenticated
            } catch (e: Exception) {
                Log.e("User sign out failed: ", e.toString())
                _authUiState.value = AuthUiState.Error(
                    messageId = 4,
                    message = "Something went wrong. Please try again later."
                )
            }
        }
    }

    // Is Context really needed here?
    fun googleSignIn(activityContext: Context) {
        val credentialManager = CredentialManager.create(activityContext)
        val (rawNonce, hashedNonce) = generateHashedNonce()
        val setServerClientId = BuildConfig.SERVER_CLIENT_ID
        val request: GetCredentialRequest = buildGoogleSignInRequest(
            alreadyUser = false,
            autoSelectAccount = true,
            serverClientId = setServerClientId,
            hashedNonce = hashedNonce
        )

        viewModelScope.launch {
            try {
                // Only this result should be used in the view model
                // Since it needs the context. See https://developer.android.com/training/sign-in/passkeys#add-support-dal
                val result = credentialManager.getCredential(
                    request = request,
                    context = activityContext,
                )
                val googleIdTokenCredential = GoogleIdTokenCredential
                    .createFrom(result.credential.data)
                val googleIdToken = googleIdTokenCredential.idToken

                // Handle sign-in with Google
                supabase.auth.signInWith(IDToken) {
                    idToken = googleIdToken
                    provider = Google
                    nonce = rawNonce
                }
                _authUiState.value = AuthUiState.Authenticated

                // Handle successful sign-in
            } catch (e: GetCredentialCancellationException) {
                // Do nothing. User chose to cancel sign-in with Google
                return@launch
            } catch (e: NoCredentialException) {
                _authUiState.value = AuthUiState.Error(
                    messageId = 5,
                    message = "No Google account found."
                )
            } catch (e: GetCredentialException) {
                // Handle GetCredentialException thrown by `credentialManager.getCredential()`
                Log.e("GetCredentialException", e.toString())
//
//            } catch (e: IllegalArgumentException) {
//                // Handle IllegalArgumentException thrown by `GoogleIdTokenCredential.createFrom()`
//                Log.e("GoogleIdTokenCredential", e.toString())
//            } catch (e: GoogleIdTokenParsingException) {
//                // Handle GoogleIdTokenParsingException thrown by `GoogleIdTokenCredential.createFrom()`
//                Log.e("GoogleIdTokenParsingException", e.toString())
            } catch (e: RestException) {
                // Handle RestException thrown by Supabase
                Log.e("RestException", e.toString())
            } catch (e: Exception) {
                // Handle unknown exceptions
                Log.e("Exception", e.toString())
            }
        }
    }

    fun passkeySignIn() {
        // TODO
    }

}
