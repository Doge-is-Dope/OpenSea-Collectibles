package com.chunchiehliang.openseacollectibles.network

import com.chunchiehliang.openseacollectibles.BuildConfig.OPENSEA_API_KEY
import com.chunchiehliang.openseacollectibles.network.service.AssetService
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
import java.util.concurrent.TimeUnit

object OpenSeaApi : KoinComponent {
    private const val BASE_URL = "https://api.opensea.io/api/v1/"

    private val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .add(Date::class.java, Rfc3339DateJsonAdapter())
        .build()

    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(
            Interceptor { chain ->
                val builder = chain.request().newBuilder()
                builder.addHeader("Accept", "application/json")
                builder.addHeader("X-API-KEY", OPENSEA_API_KEY)
                return@Interceptor chain.proceed(builder.build())
            })
        .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BASIC)) // add logger
        .connectTimeout(30, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .writeTimeout(30, TimeUnit.SECONDS)
        .build()

    private fun retrofit(): Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(MoshiConverterFactory.create(moshi).asLenient())
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .build()
    }

    val assetService: AssetService by lazy {
        retrofit().create(AssetService::class.java)
    }
}