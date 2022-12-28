package com.task.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.task.model.DrinkState
import com.task.repository.DrinksRepo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class DrinksViewModel(private val drinksRepo: DrinksRepo) : ViewModel() {

    private val _drinksData =
        MutableStateFlow<DrinkState>(DrinkState.Empty)
    val drinksData: StateFlow<DrinkState> = _drinksData

    fun searchDrinkByName(word: String) =
        viewModelScope.launch {
            drinksRepo.searchDrinkByName(word)
                .onStart {
                    _drinksData.value = DrinkState.Loading
                }
                .catch { e ->
                    _drinksData.value = DrinkState.Failure(e.toString())
                }
                .collect {
                    it?.let {
                        _drinksData.value = DrinkState.Success(it)
                    } ?: kotlin.run {
                        _drinksData.value = DrinkState.Failure("No Result Found!")
                    }
                }
        }

    fun searchDrinkByAlphabet(word: Char) =
        viewModelScope.launch {
            drinksRepo.searchDrinkByAlphabet(word)
                .onStart {
                    _drinksData.value = DrinkState.Loading
                }
                .catch { e ->
                    _drinksData.value = DrinkState.Failure(e.toString())
                }
                .collect {
                    it?.let {
                        _drinksData.value = DrinkState.Success(it)
                    } ?: kotlin.run {
                        _drinksData.value = DrinkState.Failure("No Result Found!")
                    }
                }
        }
}