package fi.project.petcare.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class PetCareViewModel: ViewModel() {
    // Settings
    private val _showBottomSheet = MutableStateFlow(false)
    val showBottomSheet: StateFlow<Boolean> = _showBottomSheet

    fun openRegistrationForm() {
        _showBottomSheet.value = !_showBottomSheet.value
    }
    fun closeRegistrationForm() {
        _showBottomSheet.value = false
    }

    fun handleRegistration(/* user: User */) {
        { /* TODO */ }
        closeRegistrationForm()
    }

}