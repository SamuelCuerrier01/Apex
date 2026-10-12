package cstjean.mobile2.tp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cstjean.mobile2.tp.data.Entrainement
import cstjean.mobile2.tp.data.TypeActivite
import cstjean.mobile2.tp.repository.FakeEntrainementRepository
import cstjean.mobile2.tp.repository.IEntrainementRepository
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CreationEntrainementsViewModel(
) : ViewModel() {
    private val _uiState = MutableStateFlow(CreationEntrainementUIState())
    val uiState: StateFlow<CreationEntrainementUIState> = _uiState.asStateFlow()

    var activiteEnum: TypeActivite? = null

    fun modifierTitre(titre: String) {
        _uiState.update { it.copy(titre = titre) }

        if (titre.isBlank() || titre.length < 3) {
            _uiState.update { it.copy(erreurTitre = cstjean.mobile2.tp.R.string.title_min_length) }
        } else {
            _uiState.update { it.copy(erreurTitre = null) }
        }
        verifierFormulaire()
    }

    fun modifierTypeActivite(typeActivite: TypeActivite) {
        activiteEnum = typeActivite

        _uiState.update {
            it.copy(
                typeActivite = activiteEnum,
                erreurTypeActivite = null
            )
        }
        verifierFormulaire()
    }

    fun modifierLieu(lieu: String) {
        _uiState.update { it.copy(lieu = lieu) }

        if (lieu.isBlank()) {
            _uiState.update { it.copy(erreurLieu = cstjean.mobile2.tp.R.string.place_required) }
        } else {
            _uiState.update { it.copy(erreurLieu= null) }
        }
        verifierFormulaire()
    }

    fun modifierEstExterieur(estExterieur: Boolean) {
        _uiState.update {
            it.copy(
                estExterieur = estExterieur
            )
        }
    }

    fun modifierIntensite(intensite: Float) {
        _uiState.update {
            it.copy(
                intensite = intensite
            )
        }
    }

    fun modifierNotesConsignes(notesConsignes : String) {
        _uiState.update {
            it.copy(
                notesConsignes = notesConsignes
            )
        }
    }

    fun verifierFormulaire() {
        val state = _uiState.value

        val titreValide = state.titre.isNotBlank() && state.erreurTitre == null
        val lieuValide = state.lieu.isNotBlank() && state.erreurLieu == null
        val activiteValide = activiteEnum != null && state.erreurTypeActivite == null

        val toutValide = titreValide && lieuValide && activiteValide

        _uiState.update { it.copy(formulaireValide = toutValide) }
    }

    fun creerEntrainement(onSuccess: () -> Unit) {
        val state = _uiState.value

        if (!state.formulaireValide) {
            return
        }

        viewModelScope.launch {
            _uiState.update { it.copy(isSaving = true, erreur = null) }

            try {
                FakeEntrainementRepository.ajouterEntrainement(
                    Entrainement(
                        id = 0,
                        titre = state.titre,
                        typeActivite = activiteEnum ?: TypeActivite.COURSE,
                        lieu = state.lieu,
                        estExterieur = state.estExterieur,
                        intensite = state.intensite.toInt(),
                        notesConsignes = state.notesConsignes
                    )
                )

                onSuccess()

            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        isSaving = false,
                        erreur = e.message
                    )
                }
            }
        }
    }

}
