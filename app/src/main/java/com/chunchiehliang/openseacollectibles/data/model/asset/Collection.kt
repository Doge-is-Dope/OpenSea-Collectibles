package com.chunchiehliang.openseacollectibles.data.model.asset

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

/**
 *
 */
@JsonClass(generateAdapter = true)
data class Collection(
    @Json(name = "name") val name: String,
)
