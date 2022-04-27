package com.chunchiehliang.openseacollectibles.network.service

import com.chunchiehliang.openseacollectibles.data.model.asset.Asset
import com.chunchiehliang.openseacollectibles.data.model.assets.AssetPagingResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


/**
 * Asset related
 */
interface AssetService {
    @GET("assets")
    suspend fun getAssets(
        @Query("owner") owner: String,
        @Query("cursor") nextPage: String? = null,
        @Query("limit") itemsPerPage: Int? = 20,
    ): AssetPagingResponse

    @GET("asset/{asset_contract_address}/{token_id}")
    suspend fun getAssetDetails(
        @Path("asset_contract_address") contractAddress: String,
        @Path("token_id") tokenId: String,
    ): Asset
}