package cstjean.mobile2.tp.viewmodel;

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cstjean.mobile2.tp.repository.FakeEntrainementRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class AccueilViewModel(
) : ViewModel() {
    private val _uiState = MutableStateFlow(AccueilUIState())
    val uiState: StateFlow<AccueilUIState> = _uiState.asStateFlow()

    init {
        chargerEntrainements()
    }

     fun chargerEntrainements(){
        viewModelScope.launch {
            _uiState.update {
                it.copy(isLoading = true)
            }

            try {
                val entrainements = FakeEntrainementRepository.chargerEntrainements()

                _uiState.update {
                    it.copy(entrainements = entrainements, isLoading = false)
                }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(erreur = e.message, isLoading = false)
                }
            }
        }
    }
}
