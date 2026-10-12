package cstjean.mobile2.tp.database.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import cstjean.mobile2.tp.data.TypeActivite

@Entity(tableName = "entrainements")
data class EntrainementEntity(
    @PrimaryKey(autoGenerate = true)
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


