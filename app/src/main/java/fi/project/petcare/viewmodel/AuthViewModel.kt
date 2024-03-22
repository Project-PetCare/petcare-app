package fi.project.petcare.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

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
    fun openRegisterSheet() {
        _authMode.value = AuthMode.REGISTER
        _showBottomSheet.value = !_showBottomSheet.value
    }
    fun closeBottomSheet() {
        _showBottomSheet.value = false
    }


    fun signUp(email: String, password: String) {
        closeBottomSheet()
        viewModelScope.launch {
            // TODO
        }
    }

    fun signIn(email: String, password: String) {
        closeBottomSheet()
        viewModelScope.launch {
            // TODO
        }
    }

}