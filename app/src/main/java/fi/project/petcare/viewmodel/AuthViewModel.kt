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

    enum class AuthMode {
        LOGIN,
        REGISTER
    }

    private val _authMode = MutableStateFlow(AuthMode.LOGIN)
    val authMode: StateFlow<AuthMode> = _authMode

    // Sign up bottom sheet
    private val _showBottomSheet = MutableStateFlow(false)
    val showBottomSheet: StateFlow<Boolean> = _showBottomSheet

    fun openLoginSheet() {
        _authMode.value = AuthMode.LOGIN
        _showBottomSheet.value = !_showBottomSheet.value
    }
    fun openRegisterSheet() {
        _authMode.value = AuthMode.REGISTER
        _showBottomSheet.value = !_showBottomSheet.value
    }
    fun closeBottomSheet() {
        _showBottomSheet.value = false
    }


}