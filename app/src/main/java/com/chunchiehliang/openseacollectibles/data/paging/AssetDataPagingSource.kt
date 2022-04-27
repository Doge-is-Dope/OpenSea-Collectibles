package com.chunchiehliang.openseacollectibles.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.chunchiehliang.openseacollectibles.data.model.asset.Asset
import com.chunchiehliang.openseacollectibles.network.service.AssetService
import retrofit2.HttpException
import timber.log.Timber
import java.io.IOException


/**
 * Data paging source for retrieving [Asset]
 */
class AssetDataPagingSource(private val owner: String, private val api: AssetService) :
    PagingSource<String, Asset>() {

    override suspend fun load(params: LoadParams<String>): LoadResult<String, Asset> {
        val position = params.key
        return try {
            Timber.d("AssetDataPagingSource - position: $position, size: ${params.loadSize}")
            val response = api.getAssets(owner, position, params.loadSize)
            LoadResult.Page(
                data = response.assets,
                prevKey = if (response.previous.isNullOrEmpty()) null else response.previous,
                nextKey = if (response.next.isNullOrEmpty()) null else response.next
            )
        } catch (exception: HttpException) {
            Timber.e("HttpException: $exception")
            return LoadResult.Error(exception)
        } catch (exception: IOException) {
            Timber.e("IOException: $exception")
            return LoadResult.Error(exception)
        } catch (exception: Exception) {
            Timber.e("Exception: $exception")
            return LoadResult.Error(exception)
        }
    }

    // The refresh key is used for subsequent refresh calls to PagingSource.load after the initial load
    override fun getRefreshKey(state: PagingState<String, Asset>): String? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey
                ?: state.closestPageToPosition(anchorPosition)?.nextKey
        }
    }
}