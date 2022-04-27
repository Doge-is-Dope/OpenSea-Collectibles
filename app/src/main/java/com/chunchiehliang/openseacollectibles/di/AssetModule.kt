package com.chunchiehliang.openseacollectibles.di

import com.chunchiehliang.openseacollectibles.data.repository.AssetRepository
import com.chunchiehliang.openseacollectibles.network.OpenSeaApi
import com.chunchiehliang.openseacollectibles.ui.assetdetails.AssetDetailsViewModel
import com.chunchiehliang.openseacollectibles.ui.assets.AssetsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val assetModule = module {
    single { AssetRepository(OpenSeaApi.assetService) }
    viewModel { AssetsViewModel(get(), get()) }
    viewModel { AssetDetailsViewModel(get()) }
}