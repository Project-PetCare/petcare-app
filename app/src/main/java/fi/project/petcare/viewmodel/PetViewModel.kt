package fi.project.petcare.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import fi.project.petcare.BuildConfig
import fi.project.petcare.model.data.PetResponse
import fi.project.petcare.model.repository.PetRepository
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.postgrest.Postgrest
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

sealed interface PetUiState {
    data object Loading : PetUiState
    data class Success(val pets: List<PetResponse.Pet>) : PetUiState
    data class Error(val message: String) : PetUiState
}

class PetViewModel: ViewModel() {
    private val apiUrl = BuildConfig.SUPABASE_URL
    private val apiKey = BuildConfig.SUPABASE_KEY
    private val client = createSupabaseClient(apiUrl, apiKey) {
        install(Postgrest)
    }

    private val _petUiState = MutableStateFlow<PetUiState>(PetUiState.Loading)
    val petUiState: StateFlow<PetUiState> = _petUiState

    init {
        getPet()
    }

    private fun getPet() {
        viewModelScope.launch {
            val result = PetRepository(client).getPets()
            Log.i("PetViewModel", "getPet: $result")
            result.fold(
                onSuccess = { pets -> _petUiState.value = PetUiState.Success(pets) },
                onFailure = { e ->
                    _petUiState.value = PetUiState.Error(e.message ?: "Unknown error")
                }
            )
        }
    }

}