package com.chunchiehliang.openseacollectibles.di

import com.chunchiehliang.openseacollectibles.data.repository.EthereumRepository
import com.chunchiehliang.openseacollectibles.network.InfuraApi
import org.koin.dsl.module

val appModule = module {
    single { EthereumRepository(InfuraApi.ethService) }
}