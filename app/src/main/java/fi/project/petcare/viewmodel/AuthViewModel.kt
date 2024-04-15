package fi.project.petcare.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import fi.project.petcare.BuildConfig
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
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.put

sealed class AuthUiState {
    object Unauthenticated : AuthUiState()
    object Authenticated : AuthUiState()
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

    fun signUp(userName: String?, userEmail: String, userPassword: String) {
        _authUiState.value = AuthUiState.Loading
        viewModelScope.launch {
            try {
                val user = supabase.auth.signUpWith(Email) {
                    email = userEmail
                    password = userPassword
                    data = buildJsonObject {
                        put("full_name", userName ?: userEmail)
                    }
                }
                Log.i("User registered returns: ", user.toString())
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
                _authUiState.value = AuthUiState.Error(
                    messageId = 2,
                    message = "Something went wrong. Please try again later."
                )
            } catch (e: Exception) {
                Log.e("User sign in failed: ", e.toString())
                _authUiState.value = AuthUiState.Error(
                    messageId = 2,
                    message = "Something went wrong. Please try again later."
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
                    messageId = 3,
                    message = "Something went wrong. Please try again later."
                )
            }
        }
    }

    // See https://developer.android.com/training/sign-in/passkeys#add-support-dal
    fun googleSignIn(rawNonce: String, googleIdToken: String) {
        viewModelScope.launch {
            try {
                supabase.auth.signInWith(IDToken) {
                    idToken = googleIdToken
                    provider = Google
                    nonce = rawNonce
                }
                _authUiState.value = AuthUiState.Authenticated
            } catch (e: RestException) {
                _authUiState.value = AuthUiState.Error(
                    messageId = 3,
                    message = "We are fixing some issues. Please try again later."
                )
                Log.e("RestException", e.toString())
            } catch (e: Exception) {
                Log.e("Exception", e.toString())
                _authUiState.value = AuthUiState.Error(
                    messageId = 3,
                    message = "Something went wrong. Please try again later."
                )
            }
        }
    }
}
