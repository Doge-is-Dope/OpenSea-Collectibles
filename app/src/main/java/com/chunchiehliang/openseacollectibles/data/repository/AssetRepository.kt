package com.chunchiehliang.openseacollectibles.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.chunchiehliang.openseacollectibles.data.model.asset.Asset
import com.chunchiehliang.openseacollectibles.data.model.common.ErrorEntity
import com.chunchiehliang.openseacollectibles.data.model.common.Result
import com.chunchiehliang.openseacollectibles.data.paging.AssetDataPagingSource
import com.chunchiehliang.openseacollectibles.network.service.AssetService
import kotlinx.coroutines.flow.Flow
import retrofit2.HttpException
import timber.log.Timber

private const val ASSET_PAGE_SIZE = 20

class AssetRepository(private val assetService: AssetService) {

    /**
     * Retrieve the assets by [ownerAddress]
     */
    fun getAssetsByAddress(ownerAddress: String): Flow<PagingData<Asset>> =
        Pager(
            config = PagingConfig(
                pageSize = ASSET_PAGE_SIZE, enablePlaceholders = false
            ),
            pagingSourceFactory = { AssetDataPagingSource(ownerAddress, assetService) }
        ).flow


    /**
     * Get asset details by [contactAddress] and [tokenId]
     */
    suspend fun getAssetDetails(contactAddress: String, tokenId: String): Result<Asset> =
        try {
            val result = assetService.getAssetDetails(contactAddress, tokenId)
            Result.Success(data = result)
        } catch (e: HttpException) {
            if (e.code() == 401) Result.Error(ErrorEntity.ApiError.Unauthenticated)
            else Result.Error(ErrorEntity.ApiError.NetworkError, e.localizedMessage)
        } catch (e: Exception) {
            Timber.e("Error: $e")
            Result.Error(ErrorEntity.ApiError.UnknownError)
        }
}