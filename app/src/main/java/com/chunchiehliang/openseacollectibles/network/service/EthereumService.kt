package com.chunchiehliang.openseacollectibles.network.service

import com.chunchiehliang.openseacollectibles.data.model.common.Result
import com.chunchiehliang.openseacollectibles.data.model.eth.InfuraBody
import com.chunchiehliang.openseacollectibles.data.model.eth.InfuraResponse
import retrofit2.http.Body
import retrofit2.http.POST


/**
 * Infura/Ethereum related
 */
interface EthereumService {
    @POST("/")
    suspend fun getBalance(
        @Body body: InfuraBody,
    ): InfuraResponse
}