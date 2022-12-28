package com.task.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.task.model.Drink
import com.task.model.DrinkState
import com.task.repository.FavDrinksRepo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class FavDrinksViewModel(private val drinksRepo: FavDrinksRepo) : ViewModel() {

    private val _favDrinksData =
        MutableStateFlow<DrinkState>(DrinkState.Empty)
    val favDrinksData: StateFlow<DrinkState> = _favDrinksData

    fun updateFavItem(model: Drink) =
        viewModelScope.launch {
            isContainItem(model) {
                if (!it)
                    insertFavItem(model)
                else
                    delFavItem(model)
            }
        }

    fun isContainItem(model: Drink, listener: (Boolean) -> Unit) =
        viewModelScope.launch {
            drinksRepo.isContainItem(model.idDrink).collect {
                listener.invoke(it)
            }
        }

    private fun delFavItem(model: Drink) =
        viewModelScope.launch {
            drinksRepo.delFavItem(model).collect {}
        }

    private fun insertFavItem(model: Drink) =
        viewModelScope.launch {
            drinksRepo.insertFavItem(model).collect {}
        }

    fun getAllFavDrinks() =
        viewModelScope.launch {
            drinksRepo.getAllFavDrinks()
                .onStart {
                    _favDrinksData.value = DrinkState.Loading
                }
                .catch { e ->
                    _favDrinksData.value = DrinkState.Failure(e.toString())
                }
                .collect {
                    _favDrinksData.value = DrinkState.SuccessDrinks(it)
                }
        }
}