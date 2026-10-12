package cstjean.mobile2.tp.database

import android.content.Context
import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import cstjean.mobile2.tp.database.dao.EntrainementDao
import cstjean.mobile2.tp.database.entity.EntrainementEntity

@Database(entities = [EntrainementEntity::class],
    version = 2,
    autoMigrations = [
        AutoMigration(from = 1, to = 2)
    ],
    exportSchema = true)
abstract class EntrainementDatabase : RoomDatabase() {

    abstract fun entrainementDao(): EntrainementDao

    companion object {
        @Volatile
        private var INSTANCE: EntrainementDatabase? = null

        fun getInstance(context: Context): EntrainementDatabase {
            return INSTANCE ?: synchronized(this) {
                Room.databaseBuilder(
                    context.applicationContext,
                    EntrainementDatabase::class.java,
                    "entrainements_database.db"
                ).build().also { INSTANCE = it }
            }
        }
    }
}
