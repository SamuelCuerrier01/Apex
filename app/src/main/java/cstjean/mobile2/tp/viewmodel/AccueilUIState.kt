package cstjean.mobile2.tp.viewmodel

import cstjean.mobile2.tp.data.Entrainement

data class AccueilUIState (
    val entrainements: List<Entrainement> = emptyList(),
    val isLoading: Boolean = false,
    val erreur: String? = null
){
    val entrainementsCompletes: List<Entrainement>
        get() = entrainements.filter { it.estComplete }

    val progressRatio: Float
        get() = if (entrainements.isNotEmpty()) entrainementsCompletes.size.toFloat() / entrainements.size else 0f


    val dernierEntrainement: Entrainement?
        get() = entrainements.lastOrNull()
}