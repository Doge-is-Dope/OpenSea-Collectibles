package com.chunchiehliang.openseacollectibles.data.model.assets

import com.chunchiehliang.openseacollectibles.data.model.asset.Asset
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class AssetPagingResponse(
    @Json(name = "next") val next: String?,
    @Json(name = "previous") val previous: String?,
    @Json(name = "assets") val assets: List<Asset>,
)