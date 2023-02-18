package com.example.trajectoryolympiad.data.network

sealed class Response<out T : Any> {
    data class Success<out T : Any>(val data: T) : Response<T>()
    data class NoInternetError(val message: String, val cause: Exception) : Response<Nothing>()
    data class ApiError(val message: String, val cause: Exception) : Response<Nothing>()
    data class UnexpectedError(val message: String, val cause: Exception) : Response<Nothing>()

}