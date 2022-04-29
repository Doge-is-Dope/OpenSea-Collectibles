package com.chunchiehliang.openseacollectibles.network

import com.chunchiehliang.openseacollectibles.network.service.EthereumService
import com.chunchiehliang.openseacollectibles.util.INFURA_PROJECT_ID
import com.squareup.moshi.Moshi
import com.squareup.moshi.adapters.Rfc3339DateJsonAdapter
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.component.KoinComponent
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.*

object InfuraApi : KoinComponent {
    private const val BASE_URL = "https://mainnet.infura.io/"

    private val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .add(Date::class.java, Rfc3339DateJsonAdapter())
        .build()

    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(
            Interceptor { chain ->
                val request = chain.request()
                val builder = request.newBuilder()
                builder.addHeader("Accept", "application/json")
                val newUrl = request.url.newBuilder()
                    .addPathSegments("v3/$INFURA_PROJECT_ID")
                    .build()
                return@Interceptor chain.proceed(builder.url(newUrl).build())
            })
        .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BASIC)) // add logger
        .build()

    private fun retrofit(): Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(MoshiConverterFactory.create(moshi).asLenient())
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .build()
    }

    val ethService: EthereumService by lazy {
        retrofit().create(EthereumService::class.java)
    }
}