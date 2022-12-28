package com.task.api

import retrofit2.Response

abstract class DrinksApiRequest {
    suspend fun <T : Any> apiRequest(call: suspend () -> Response<T>): T? {
        val response = call.invoke()
        return if (response.isSuccessful) {
            response.body()
        } else {
            null
        }
    }
}