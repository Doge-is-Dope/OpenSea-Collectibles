package com.chunchiehliang.openseacollectibles.data.repository

import com.chunchiehliang.openseacollectibles.data.model.common.ErrorEntity
import com.chunchiehliang.openseacollectibles.data.model.common.Result
import com.chunchiehliang.openseacollectibles.data.model.eth.InfuraBody
import com.chunchiehliang.openseacollectibles.data.model.eth.InfuraResponse
import com.chunchiehliang.openseacollectibles.network.service.EthereumService
import com.chunchiehliang.openseacollectibles.util.BLOCK_PARAMETER_LATEST
import com.chunchiehliang.openseacollectibles.util.JSON_RPC_METHOD_GET_BALANCE
import retrofit2.HttpException
import timber.log.Timber

class EthereumRepository(private val ethService: EthereumService) {

    /**
     * Retrieve the balance by [address]
     */
    suspend fun getBalance(address: String): Result<InfuraResponse> =
        try {
            val result = ethService.getBalance(InfuraBody(
                params = listOf(address, BLOCK_PARAMETER_LATEST),
                method = JSON_RPC_METHOD_GET_BALANCE))
            Result.Success(data = result)
        } catch (e: HttpException) {
            if (e.code() == 401) Result.Error(ErrorEntity.ApiError.Unauthenticated)
            else Result.Error(ErrorEntity.ApiError.NetworkError, e.localizedMessage)
        } catch (e: Exception) {
            Timber.e("Error: $e")
            Result.Error(ErrorEntity.ApiError.UnknownError)
        }
}