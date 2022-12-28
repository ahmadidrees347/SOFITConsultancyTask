package com.task.dao

import androidx.room.*
import com.task.model.Drink

@Dao
interface FavDao {
    @Query("SELECT * FROM drink")
    fun getAll(): MutableList<Drink>

    @Insert
    fun insert(vararg models: Drink)

    @Delete
    fun delete(model: Drink)

    @Query("DELETE FROM drink")
    fun deleteAll()

    @Query("SELECT EXISTS(SELECT * FROM drink WHERE idDrink = :id)")
    fun isContainItem(id: String): Boolean
}