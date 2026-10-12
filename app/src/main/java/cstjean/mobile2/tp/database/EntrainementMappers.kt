package cstjean.mobile2.tp.database

import cstjean.mobile2.tp.data.Entrainement
import cstjean.mobile2.tp.data.TypeActivite
import cstjean.mobile2.tp.database.entity.EntrainementEntity


fun EntrainementEntity.toModel(): Entrainement {
    return Entrainement(
        id = this.id,
        titre = this.titre,
        typeActivite = this.typeActivite,
        lieu = this.lieu,
        estExterieur = this.estExterieur,
        intensite = this.intensite,
        notesConsignes = this.notesConsignes,
    )
}

fun Entrainement.toEntity(): EntrainementEntity {
    return EntrainementEntity(
        id = this.id,
        titre = this.titre,
        typeActivite = this.typeActivite,
        lieu = this.lieu,
        estExterieur = this.estExterieur,
        intensite = this.intensite,
        notesConsignes = this.notesConsignes,
    )
}