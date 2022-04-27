package com.chunchiehliang.openseacollectibles.data.model.eth

import com.squareup.moshi.Json

data class InfuraBody(
    @Json(name = "id") val id: Int? = 1,
    @Json(name = "jsonrpc") val jsonRPC: String? = "2.0",
    @Json(name = "method") val method: String,
    @Json(name = "params") val params: List<String>,
)
