package com.task.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class DrinkModel(
    @SerializedName("drinks")
    @Expose
    var drinks: List<Drink> = mutableListOf()
)

@Entity
data class Drink(
    @SerializedName("idDrink")
    @Expose
    @PrimaryKey
    @ColumnInfo
    val idDrink: String,
    @SerializedName("strDrinkThumb")
    @Expose
    @ColumnInfo
    val strDrinkThumb: String,
    @SerializedName("strDrink")
    @Expose
    @ColumnInfo
    val strDrink: String,
    @SerializedName("strAlcoholic")
    @Expose
    @ColumnInfo
    val strAlcoholic: String,
    @SerializedName("strInstructions")
    @Expose
    @ColumnInfo
    val strInstructions: String,
) {

    @Ignore
    var isFav: Boolean = false
}