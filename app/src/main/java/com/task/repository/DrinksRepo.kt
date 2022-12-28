package com.task.repository

import com.task.api.DrinksApi
import com.task.api.DrinksApiRequest
import com.task.model.DrinkModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class DrinksRepo(private val api: DrinksApi) : DrinksApiRequest(){

    fun searchDrinkByName(word: String)
            : Flow<DrinkModel?> = flow {
        emit(apiRequest { api.searchDrinkByName(word) })
    }.flowOn(Dispatchers.Default)

    fun searchDrinkByAlphabet(word: Char)
            : Flow<DrinkModel?> = flow {
        emit(apiRequest { api.searchDrinkByAlphabet(word) })
    }.flowOn(Dispatchers.Default)

}