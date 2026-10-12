package cstjean.mobile2.tp.viewmodel

import cstjean.mobile2.tp.data.Entrainement

data class EntrainementUIState (
    val entrainement: Entrainement? = null,
    val isLoading: Boolean = false,
    val erreur: String? = null,
)