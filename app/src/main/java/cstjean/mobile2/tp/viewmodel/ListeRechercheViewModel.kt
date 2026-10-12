package cstjean.mobile2.tp.viewmodel;

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cstjean.mobile2.tp.data.Entrainement
import cstjean.mobile2.tp.data.FiltreRecherche
import cstjean.mobile2.tp.data.TypeActivite
import cstjean.mobile2.tp.repository.FakeEntrainementRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ListeRechercheViewModel(
) : ViewModel() {
    private val _uiState = MutableStateFlow(ListeRechercheUIState())
    val uiState: StateFlow<ListeRechercheUIState> = _uiState.asStateFlow()
    val filtresActifs = _uiState.value.filtresActives.toMutableList()

    init {
        chargerEntrainements()
    }


    fun chargerEntrainementRecherche(input: String){
        viewModelScope.launch {
            _uiState.update {
                it.copy(isLoading = true)
            }

            try {
                val entrainements = FakeEntrainementRepository.chargerEntrainements()

                val entrainementsFiltres = entrainements.filter {
                    it.titre.contains(input, ignoreCase = true)
                }

                _uiState.update {
                    it.copy(inputValue = input)
                }

                chargerEntrainementFiltre(null, entrainementsFiltres, true)

            } catch (e: Exception) {
                _uiState.update {
                    it.copy(erreur = e.message, isLoading = false)
                }
            }
        }
    }

    fun chargerEntrainementFiltre(input: Enum<*>?, entrainementsRecherche: List<Entrainement> = emptyList(), rechercheActive: Boolean = false){
        viewModelScope.launch {
            _uiState.update {
                it.copy(isLoading = true)
            }
            try {
                var entrainements: List<Entrainement>

                if (entrainementsRecherche.isNotEmpty() || rechercheActive){
                    entrainements = entrainementsRecherche
                } else {
                    entrainements = FakeEntrainementRepository.chargerEntrainements()
                }

                modifierFiltreActif(input)

                if (filtresActifs.contains(FiltreRecherche.FAVORIS)){
                    entrainements = entrainements.filter { it.estFavoris } as MutableList<Entrainement>
                }

                val typesActifs = TypeActivite.entries.filter { filtresActifs.contains(it) }

                if (typesActifs.isNotEmpty()) {
                    entrainements = entrainements.filter { entrainement ->
                        typesActifs.contains(entrainement.typeActivite)
                    } as MutableList<Entrainement>
                }

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

    private fun modifierFiltreActif(input: Enum<*>?) {

        if (filtresActifs.contains(input)) {
            filtresActifs.remove(input)
        } else {
            if (input != null) {
                filtresActifs.add(input)
            }
        }

        if(input == FiltreRecherche.TOUS && filtresActifs.size > 1){
            filtresActifs.clear()
            filtresActifs.add(input)
        }
        if (filtresActifs.size > 1 && filtresActifs.contains(FiltreRecherche.TOUS)) {
            filtresActifs.remove(FiltreRecherche.TOUS)
        }
        _uiState.update {
            it.copy(filtresActives = filtresActifs)
        }
    }

    fun toggleFavoris(entrainement: Entrainement){
        viewModelScope.launch {
            _uiState.update {
                it.copy(isLoading = true)
            }

            try {
                val entrainementMisAJour = entrainement.copy(
                    estFavoris = !entrainement.estFavoris
                )
                FakeEntrainementRepository.mettreAJourEntrainement(entrainementMisAJour)

                _uiState.update { state ->
                    state.copy(
                        entrainements = state.entrainements.map {
                            if (it.id == entrainementMisAJour.id) entrainementMisAJour else it
                        } as MutableList<Entrainement>,
                        isLoading = false
                    )
                }

            } catch (e: Exception) {
                _uiState.update {
                    it.copy(erreur = e.message, isLoading = false)
                }
            }
        }
    }
}
