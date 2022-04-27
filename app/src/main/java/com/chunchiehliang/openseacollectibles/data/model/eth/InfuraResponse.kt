package com.chunchiehliang.openseacollectibles.data.model.eth

import com.squareup.moshi.Json

data class InfuraResponse(
    @Json(name = "result") val result: String,
)
