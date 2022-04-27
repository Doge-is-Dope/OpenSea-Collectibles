package com.chunchiehliang.openseacollectibles.data.model.asset


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Contract(
    @Json(name = "address") val address: String,
    @Json(name = "name") val name: String
)