package cstjean.mobile2.tp.viewmodel

import cstjean.mobile2.tp.data.Entrainement;
import cstjean.mobile2.tp.data.FiltreRecherche
import cstjean.mobile2.tp.data.TypeActivite


data class ListeRechercheUIState (
    val entrainements: List<Entrainement> = emptyList<Entrainement>(),
    val inputValue: String = "",
    val isLoading: Boolean = false,
    val filtresActives: MutableList<Enum<*>> = mutableListOf(FiltreRecherche.TOUS),
    val erreur: String? = "",
){
    val listeTypeActivite: List<TypeActivite>
        get() = TypeActivite.entries.map { it }
}