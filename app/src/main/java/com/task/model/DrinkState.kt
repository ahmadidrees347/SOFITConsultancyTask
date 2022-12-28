package com.task.model

import androidx.annotation.Keep

@Keep
sealed class DrinkState {
    object Empty : DrinkState()
    object Loading : DrinkState()
    class Failure(val error: String) : DrinkState()
    data class Success(val drinkResponse: DrinkModel) : DrinkState()
    data class SuccessDrinks(val drinkList: List<Drink>) : DrinkState()
}