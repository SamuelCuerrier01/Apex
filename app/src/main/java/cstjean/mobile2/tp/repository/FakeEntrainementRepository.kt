package cstjean.mobile2.tp.repository

import cstjean.mobile2.tp.data.Entrainement
import cstjean.mobile2.tp.data.TypeActivite
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import kotlin.time.Duration.Companion.milliseconds

object FakeEntrainementRepository : IEntrainementRepository {
    private val fakeEntrainements = mutableListOf<Entrainement>(Entrainement(1, "Marathon", TypeActivite.COURSE, "Paris", true, 10, "Aucune consigne", false, true),
        Entrainement(2, "Course à pied", TypeActivite.COURSE, "Lyon", false, 8, "Aucune consigne", true),
        Entrainement(3, "Yoga", TypeActivite.YOGA, "Marseille", true, 6, "Aucune consigne"),
        Entrainement(4, "Natation", TypeActivite.NATATION, "Bordeaux", false, 7, "Aucune consigne", true),
        Entrainement(5, "Vélo", TypeActivite.VELO, "Lille", true, 9, "Aucune consigne"),
        Entrainement(6, "Musculation", TypeActivite.MUSCULATION, "Toulouse", false, 5, "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA"),
    )

    override suspend fun chargerEntrainements(): MutableList<Entrainement> {
        delay(1000.milliseconds)
        return fakeEntrainements
    }

    override fun observerEntrainement(): Flow<List<Entrainement>> = flow {
        emit(fakeEntrainements)
    }

    override suspend fun ajouterEntrainement(entrainement: Entrainement) {
        withContext(Dispatchers.IO) {
            delay(500.milliseconds)

            val nouvelId =
                (fakeEntrainements.maxOfOrNull { it.id } ?: 0) + 1

            fakeEntrainements.add(
                entrainement.copy(id = nouvelId)
            )
        }
    }

    override suspend fun mettreAJourEntrainement(entrainement: Entrainement) {
        withContext(Dispatchers.IO) {
            val index = fakeEntrainements.indexOfFirst { it.id == entrainement.id }
            if (index == -1) {
                error("Entraînement introuvable: ${entrainement.id}")
            }
            fakeEntrainements[index] = entrainement
        }
    }

    override suspend fun supprimerEntrainement(entrainementId: Int) {
        withContext(Dispatchers.IO) {
            fakeEntrainements.removeIf { it.id == entrainementId }
        }
    }
}