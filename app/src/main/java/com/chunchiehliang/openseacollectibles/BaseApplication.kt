package com.chunchiehliang.openseacollectibles

import android.app.Application
import com.chunchiehliang.openseacollectibles.di.appModule
import com.chunchiehliang.openseacollectibles.di.assetModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext
import org.koin.core.logger.Level
import timber.log.Timber

class BaseApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
        injectKoinModules()
    }

    private fun injectKoinModules() {
        GlobalContext.startKoin {
            androidLogger(if (BuildConfig.DEBUG) Level.ERROR else Level.NONE)
            androidContext(this@BaseApplication)
            modules(listOf(appModule, assetModule))
        }
    }
}