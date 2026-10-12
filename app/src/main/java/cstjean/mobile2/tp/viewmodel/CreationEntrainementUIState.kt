package cstjean.mobile2.tp.viewmodel

import cstjean.mobile2.tp.data.Entrainement
import cstjean.mobile2.tp.data.TypeActivite

data class CreationEntrainementUIState (
    val titre: String = "",
    val typeActivite: TypeActivite? = null,
    val lieu: String = "",
    val estExterieur: Boolean = false,
    val intensite: Float = 1f,
    val notesConsignes: String = "",
    val formulaireValide: Boolean = false,

    val erreurTitre: Int? = cstjean.mobile2.tp.R.string.title_min_length,
    val erreurTypeActivite: Int? = cstjean.mobile2.tp.R.string.activity_required,
    val erreurLieu: Int? = cstjean.mobile2.tp.R.string.place_required,
    val erreurEstExterieur: String? = null,
    val erreurIntensite: String? = null,
    val erreurNotesConsignes: String? = null,
    val isSaving: Boolean = false,
    val erreur: String? = null
){
    val listeTypeActivite: List<TypeActivite>
        get() = TypeActivite.entries
}