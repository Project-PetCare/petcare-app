package fi.project.petcare.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class AuthViewModel: ViewModel() {
    enum class AuthMode {
        LOGIN,
        REGISTER
    }

    private val _authMode = MutableStateFlow(AuthMode.LOGIN)
    val authMode: StateFlow<AuthMode> = _authMode

    // Sign up/in bottom sheet
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

    private val _loginEmail = MutableStateFlow("")
    private val _loginPassword = MutableStateFlow("")
    val loginEmail: StateFlow<String> = _loginEmail
    val loginPassword: StateFlow<String> = _loginPassword

    fun onLoginEmailChange(email: String) {
        _loginEmail.value = email
    }
    fun onLoginPasswordChange(password: String) {
        _loginPassword.value = password
    }

    private val _registerEmail = MutableStateFlow("")
    private val _registerPassword = MutableStateFlow("")
    val registerEmail: StateFlow<String> = _registerEmail
    val registerPassword: StateFlow<String> = _registerPassword
    fun onRegisterEmailChange(email: String) {
        _registerEmail.value = email
    }
    fun onRegisterPasswordChange(password: String) {
        _registerPassword.value = password
    }


    fun signUpNewUser(/* user: User */) {
        closeBottomSheet()
    }

    fun signIn(/* user: User */) {
        closeBottomSheet()

    }

}