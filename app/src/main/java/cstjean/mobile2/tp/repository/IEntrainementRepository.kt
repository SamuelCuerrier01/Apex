package cstjean.mobile2.tp.repository

import cstjean.mobile2.tp.data.Entrainement
import kotlinx.coroutines.flow.Flow

interface IEntrainementRepository {
    suspend fun chargerEntrainements(): List<Entrainement>
    fun observerEntrainement(): Flow<List<Entrainement>>

    suspend fun trouverEntrainement(id: Int): Entrainement?

    suspend fun supprimerEntrainement(id: Int)

    suspend fun ajouterEntrainement(film: Entrainement)
}