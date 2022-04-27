package com.chunchiehliang.openseacollectibles.data.model.common

sealed class ErrorEntity {
    sealed class DataError : ErrorEntity() {
        object GeneralError : DataError()
    }

    sealed class ApiError : ErrorEntity() {
        object Unauthenticated : ApiError()

        object NetworkError : ApiError()

        object UnknownError : ApiError()
    }
}