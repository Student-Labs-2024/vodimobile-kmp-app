package com.vodimobile

import android.app.Application
import com.vodimobile.di.androidModule
import com.vodimobile.di.validatorModule
import com.vodimobile.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class App : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@App)
            androidLogger(level = Level.INFO) //Change it on release build to Level.NONE
            modules(viewModelModule, validatorModule, androidModule)
        }
    }
}