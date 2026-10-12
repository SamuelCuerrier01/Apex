package cstjean.mobile2.tp.ui.navigation

import kotlinx.serialization.Serializable

@Serializable
object CreationEntrainementRoute
@Serializable
object AccueilRoute

@Serializable
data class EntrainementRoute(val idEntrainement: Int)

@Serializable
object ListeRechercheRoute