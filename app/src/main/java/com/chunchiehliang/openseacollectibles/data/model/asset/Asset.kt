package com.chunchiehliang.openseacollectibles.data.model.asset


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

/**
 * Data model returned by getAssets and getAssetDetails
 */
@JsonClass(generateAdapter = true)
data class Asset(
    @Json(name = "id") val id: Int,
    @Json(name = "token_id") val tokenId: String,
    @Json(name = "name") val name: String,
    @Json(name = "description") val description: String?,
    @Json(name = "image_preview_url") val imagePreviewUrl: String,
    @Json(name = "image_url") val imageUrl: String,
    @Json(name = "permalink") val permalink: String,

    @Json(name = "collection") val collection: Collection,
    @Json(name = "asset_contract") val contract: Contract,
)