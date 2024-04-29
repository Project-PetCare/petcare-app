package fi.project.petcare.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import fi.project.petcare.model.data.SupabaseClientFactory
import fi.project.petcare.model.data.User
import fi.project.petcare.model.data.demoUser
import fi.project.petcare.model.repository.AuthRepository
import io.github.jan.supabase.exceptions.BadRequestRestException
import io.github.jan.supabase.exceptions.UnknownRestException
import io.github.jan.supabase.gotrue.SessionSource
import io.github.jan.supabase.gotrue.SessionStatus
import io.github.jan.supabase.gotrue.auth
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

sealed interface AuthUiState {
    data object Unauthenticated : AuthUiState
    data object Loading : AuthUiState
    data class Authenticated(val user: User) : AuthUiState
    data class Error(val message: String) : AuthUiState
}

class AuthViewModel: ViewModel() {
    val client = SupabaseClientFactory.getInstance()

    private val _authUiState = MutableStateFlow<AuthUiState>(AuthUiState.Unauthenticated)
    val authUiState: StateFlow<AuthUiState> = _authUiState

    init {
        getCurrentUser()
    }

    fun onDemoSignIn() {
        _authUiState.value = AuthUiState.Authenticated(
            User(demoUser.id, demoUser.name, demoUser.email)
        )
    }

    private fun getCurrentUser() {
        viewModelScope.launch {
            client.auth.sessionStatus.collect { sessionStatus ->
                when (sessionStatus) {
                    is SessionStatus.LoadingFromStorage -> {
                        _authUiState.value = AuthUiState.Loading
                    }
                    is SessionStatus.Authenticated -> {
                        when (sessionStatus.source) {
                            is SessionSource.Storage -> {
                                _authUiState.value = AuthUiState.Authenticated(
                                    User(
                                        id = sessionStatus.session.user?.id.toString(),
                                        name = sessionStatus
                                            .session.user?.userMetadata?.get("full_name").toString(),
                                        email = sessionStatus.session.user?.email.toString()
                                    )
                                )
                            }
                            is SessionSource.SignUp -> {
                                _authUiState.value = AuthUiState.Error(
                                    message = "Please verify your email address."
                                )
                            }

                            SessionSource.AnonymousSignIn -> TODO()
                            SessionSource.External -> TODO()
                            is SessionSource.Refresh ->
                                _authUiState.value = AuthUiState.Authenticated(
                                    User(
                                        id = sessionStatus.session.user?.id.toString(),
                                        name = sessionStatus
                                            .session.user?.userMetadata?.get("full_name").toString(),
                                        email = sessionStatus.session.user?.email.toString()
                                    )
                                )
                            is SessionSource.SignIn -> {
                                _authUiState.value = AuthUiState.Authenticated(
                                    User(
                                        id = sessionStatus.session.user?.id.toString(),
                                        name = sessionStatus
                                            .session.user?.userMetadata?.get("full_name").toString(),
                                        email = sessionStatus.session.user?.email.toString()
                                    )
                                )
                            }
                            SessionSource.Unknown -> TODO()
                            is SessionSource.UserChanged -> TODO()
                            is SessionSource.UserIdentitiesChanged -> TODO()
                        }
                    }
                    is SessionStatus.NetworkError -> {
                        _authUiState.value = AuthUiState.Error(
                            message = "Please check your internet connection."
                        )
                    }
                    is SessionStatus.NotAuthenticated -> {
                        _authUiState.value = AuthUiState.Unauthenticated
                    }
                }
            }
        }
    }

    fun onSignUp(userName: String?, userEmail: String, userPassword: String) {
        _authUiState.value = AuthUiState.Loading
        viewModelScope.launch {
            val result = AuthRepository(client).signUp(userName, userEmail, userPassword)
            result.fold(
                onSuccess = { _ ->
                    _authUiState.value = AuthUiState.Error(
                        message = "Please verify your email address."
                    )
                },
                onFailure = { error ->
                    when (error) {
                        is UnknownRestException -> {
                            if (error.message?.contains("Email rate limit exceeded") == true) {
                                _authUiState.value = AuthUiState.Error(
                                    message = "Please try again later."
                                )
                            }
                            Log.e("AuthViewModel", "Error registering user", error)
                            _authUiState.value = AuthUiState.Error(
                                message = "Something went wrong. Please try again later."
                            )
                        }
                    }
                }
            )
        }
    }

    fun onSignIn(userEmail: String, userPassword: String) {
        _authUiState.value = AuthUiState.Loading
        viewModelScope.launch {
            val result = AuthRepository(client).signIn(userEmail, userPassword)
            result.fold(
                onSuccess = { _ ->
                    getCurrentUser()
                },
                onFailure = { error ->
                    when (error) {
                        is BadRequestRestException -> {
                            if (error.message?.contains("Email not confirmed") == true) {
                                _authUiState.value = AuthUiState.Error(
                                    message = "Please verify your email address."
                                )
                            } else if (error.message?.contains("Invalid login credentials") == true) {
                                _authUiState.value = AuthUiState.Error(
                                    message = "Email or password is incorrect."
                                )
                            }
                        }
                        else -> {
                            Log.e("User sign in failed: ", error.toString())
                            _authUiState.value = AuthUiState.Error(
                                message = "Something went wrong. Please try again later."
                            )
                        }
                    }
                }
            )
        }
    }

    fun onSignOut() {
        _authUiState.value = AuthUiState.Loading
        viewModelScope.launch {
            val result = AuthRepository(client).signOut()
            result.fold(
                onSuccess = { _ ->
                    _authUiState.value = AuthUiState.Unauthenticated
                },
                onFailure = { error ->
                    Log.e("AuthViewModel", "Error signing out", error)
                    _authUiState.value = AuthUiState.Error(
                        message = "Something went wrong. Please try again later."
                    )
                }
            )
        }
    }
}
