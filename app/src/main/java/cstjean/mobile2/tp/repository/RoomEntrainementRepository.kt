package cstjean.mobile2.tp.repository

import cstjean.mobile2.tp.data.Entrainement
import cstjean.mobile2.tp.database.dao.EntrainementDao
import cstjean.mobile2.tp.database.entity.EntrainementEntity
import cstjean.mobile2.tp.database.toModel
import cstjean.mobile2.tp.database.toEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class RoomEntrainementRepository(
    private val entrainementDao: EntrainementDao,
) : IEntrainementRepository {
    override suspend fun chargerEntrainements(): List<Entrainement> {
        return entrainementDao.chargerTousLesEntrainements().map { entity: EntrainementEntity ->
            entity.toModel()
        }
    }

    override fun observerEntrainement(): Flow<List<Entrainement>> {
        return entrainementDao.obtenirTousLesEntrainements().map { listeEntities ->
            listeEntities.map { entity : EntrainementEntity -> entity.toModel() }
        }
    }

    override suspend fun trouverEntrainement(id: Int): Entrainement? {
        return entrainementDao.trouverParId(id)?.toModel()
    }

    override suspend fun supprimerEntrainement(id: Int) {
        entrainementDao.supprimerParId(id)
    }

    override suspend fun ajouterEntrainement(entrainement: Entrainement) {
        entrainementDao.insererEntrainement(entrainement.toEntity())
    }

}
