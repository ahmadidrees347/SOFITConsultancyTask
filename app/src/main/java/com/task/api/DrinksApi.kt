package com.task.api

import com.task.model.DrinkModel
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query


interface DrinksApi {

    @GET("v1/1/search.php")
    suspend fun searchDrinkByName(@Query("s") query: String): Response<DrinkModel>

    @GET("v1/1/search.php")
    suspend fun searchDrinkByAlphabet(@Query("f") query: Char): Response<DrinkModel>

    companion object {
        operator fun invoke(): DrinksApi {
            return Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("https://www.thecocktaildb.com/api/json/")
                .build()
                .create(DrinksApi::class.java)
        }
    }
}

