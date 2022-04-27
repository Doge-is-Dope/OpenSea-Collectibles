package com.chunchiehliang.openseacollectibles.data.model.common

sealed class Result<out T> {

    data class Success<out T>(val data: T) : Result<T>()

    data class Error<T>(val error: ErrorEntity, val message: String? = null) : Result<T>()
}