package cstjean.mobile2.tp.viewmodel;

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cstjean.mobile2.tp.repository.FakeEntrainementRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class EntrainementViewModel(
    private val idEntrainement: Int
) : ViewModel() {
    private val _uiState = MutableStateFlow(EntrainementUIState())
    val uiState: StateFlow<EntrainementUIState> = _uiState.asStateFlow()

    init {
        chargerEntrainement(idEntrainement)
    }

    fun toggleEntrainementComplete() {
        val entrainement = _uiState.value.entrainement ?: return
        val updatedEntrainement = entrainement.copy(
            estComplete = !entrainement.estComplete
        )

        viewModelScope.launch {
            try {
                FakeEntrainementRepository.mettreAJourEntrainement(updatedEntrainement)
                _uiState.update {
                    it.copy(entrainement = updatedEntrainement, erreur = null)
                }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(erreur = e.message)
                }
            }
        }
    }

    fun deleteEntrainement(){
        viewModelScope.launch {
            try {
                FakeEntrainementRepository.supprimerEntrainement(idEntrainement)
                _uiState.update {
                    it.copy(entrainement = null, erreur = null)
                }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(erreur = e.message)
                }
            }
        }
    }

    fun chargerEntrainement(id: Int){
        viewModelScope.launch {
            _uiState.update {
                it.copy(isLoading = true)
            }

            try {
                val entrainement = FakeEntrainementRepository.chargerEntrainements().first { it.id == id }

                _uiState.update {
                    it.copy(entrainement = entrainement, isLoading = false)
                }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(erreur = e.message, isLoading = false)
                }
            }
        }
    }
}
