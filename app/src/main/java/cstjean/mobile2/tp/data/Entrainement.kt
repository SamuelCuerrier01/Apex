package cstjean.mobile2.tp.data;


data class Entrainement(
    val id: Int,
    val titre: String,
    val typeActivite: TypeActivite,
    val lieu: String,
    val estExterieur: Boolean,
    val intensite: Int,
    val notesConsignes: String,
    val estComplete: Boolean = false,
    var estFavoris: Boolean = false

)
