package com.task.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.task.dao.FavDao
import com.task.model.Drink

@Database(entities = [Drink::class], version = 1, exportSchema = false)
abstract class LocalDatabase : RoomDatabase() {
    abstract fun getFavDao(): FavDao

    companion object {
        @Volatile
        private var INSTANCE: LocalDatabase? = null
        private const val DATABASE_NAME = "TaskDB"

        fun getInstance(context: Context): LocalDatabase =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: buildDatabase(context).also {
                    INSTANCE = it
                }
            }

        private fun buildDatabase(appContext: Context): LocalDatabase {
            return Room.databaseBuilder(appContext, LocalDatabase::class.java, DATABASE_NAME)
                .fallbackToDestructiveMigration()
                .build()
        }
    }
}
