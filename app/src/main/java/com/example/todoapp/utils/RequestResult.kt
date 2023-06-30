package com.example.todoapp.utils

sealed class RequestResult<out T> {
    data class Success<T>(val value: T?) : RequestResult<T>()
    data class Error<T>(val e: RequestError) : RequestResult<T>()
}

sealed class RequestError(val code: Int) {
    object BadRequest : RequestError(400)
    object Unauthorized : RequestError(401)
    object NotFound : RequestError(404)
    object InternalServerError : RequestError(500)
}
