package com.urvish.dataprovider.utils

import org.json.JSONObject
import retrofit2.Response


sealed interface Result<out T> {
    data class Success<T>(val data: T) : Result<T>
    data class Error(var exception: Throwable? = null) : Result<Nothing>
}

inline fun <reified T : Any> Response<T>.toResult(): Result<T> {
    return if (isSuccessful && body() != null) {
        Result.Success(body()!!)
    } else {
        try {
            val jsonObject = errorBody()?.charStream()?.readText()?.let { JSONObject(it) }
            Result.Error(Throwable(jsonObject?.getString("message") ?: errorBody().toString()))
        } catch (e: Exception) {
            Result.Error(Throwable(e.message))
        }
    }
}
