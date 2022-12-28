package com.task.repository

import android.content.Context
import com.task.db.LocalDatabase
import com.task.model.Drink
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class FavDrinksRepo(private val context: Context) {
    private lateinit var localDatabase: LocalDatabase

    init {
        initializeDbIfNot()
    }

    private fun initializeDbIfNot() {
        if (!::localDatabase.isInitialized) {
            localDatabase = LocalDatabase.getInstance(context)
        }
    }

    fun isContainItem(id: String): Flow<Boolean> = flow {
        emit(localDatabase.getFavDao().isContainItem(id))
    }.flowOn(Dispatchers.Default)

    fun insertFavItem(item: Drink) = flow {
        emit(localDatabase.getFavDao().insert(item))
    }.flowOn(Dispatchers.Default)

    fun delFavItem(item: Drink) = flow {
        emit(localDatabase.getFavDao().delete(item))
    }.flowOn(Dispatchers.Default)

    fun getAllFavDrinks()
            : Flow<MutableList<Drink>> = flow {
        emit(localDatabase.getFavDao().getAll())
    }.flowOn(Dispatchers.Default)
}