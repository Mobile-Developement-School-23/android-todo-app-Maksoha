package com.example.todoapp.data.models

sealed class RequestResult<out T> {

    data class Success<T> (val value: T) : RequestResult<T>()

    data class Error<T> (val e: RequestError) : RequestResult<T>()
}

enum class RequestError(val code : Int) {
    BAD_REQUEST(400),
    UNAUTHORIZED(401),
    NOT_FOUND(404),
    INTERNAL_SERVER_ERROR(500)
}