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

    // Sign up bottom sheet
    private val _showBottomSheet = MutableStateFlow(false)
    val showBottomSheet: StateFlow<Boolean> = _showBottomSheet

    fun openLoginSheet() {
        _authMode.value = AuthMode.LOGIN
        _showBottomSheet.value = !_showBottomSheet.value
    }
    fun closeBottomSheet() {
        _showBottomSheet.value = false
    }

    fun openRegisterSheet() {
        _authMode.value = AuthMode.REGISTER
        _showBottomSheet.value = !_showBottomSheet.value
    }

    fun handleRegistration(/* user: User */) {
        { /* TODO */ }
        closeBottomSheet()
    }

    fun handleLogin(/* user: User */) {
        { /* TODO */ }
        closeBottomSheet()
    }

}