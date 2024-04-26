package fi.project.petcare.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import fi.project.petcare.model.data.PetResponse
import fi.project.petcare.model.data.SupabaseClientFactory
import fi.project.petcare.model.repository.PetRepository
import io.github.jan.supabase.gotrue.SessionStatus
import io.github.jan.supabase.gotrue.auth
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

sealed interface PetUiState {
    data object Loading : PetUiState
    data class Success(val pets: List<PetResponse.Pet>) : PetUiState
    data class Error(val message: String) : PetUiState
}

class PetViewModel: ViewModel() {
    private val client = SupabaseClientFactory.getInstance()

    private val _petUiState = MutableStateFlow<PetUiState>(PetUiState.Loading)
    val petUiState: StateFlow<PetUiState> = _petUiState

    init {
        getPet()
    }

    private fun getPet() {
        viewModelScope.launch {
            client.auth.sessionStatus.collect {
                when (it) {
                    is SessionStatus.Authenticated -> {
                        val result = PetRepository(client).getPets(it.session.user?.id.toString())
                        result.fold(
                            onSuccess = { pets -> _petUiState.value = PetUiState.Success(pets) },
                            onFailure = { e ->
                                Log.e("PetViewModel", "User not authenticated", e)
                                _petUiState.value = PetUiState.Error(e.message ?: "Unknown error")
                            }
                        )
                    }
                    else -> {
                        _petUiState.value = PetUiState.Error("User not authenticated")
                    }
                }
            }
        }
    }

    fun upsertPet(pet: PetResponse.Pet) {
        viewModelScope.launch {
            val result = PetRepository(client).upsertPet(pet)
            result.fold(
                onSuccess = { getPet() },
                onFailure = { e ->
                    Log.e("PetViewModel", "Error upserting pet:", e)
                    _petUiState.value = PetUiState.Error(e.message ?: "Unknown error")
                }
            )
        }
    }
}