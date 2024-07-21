package com.vodimobile

import android.app.Application
import com.vodimobile.di.initKoin
import com.vodimobile.di.userDataStoreRepositoryModule
import com.vodimobile.di.validatorModule
import com.vodimobile.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.logger.Level

class App : Application() {
    override fun onCreate() {
        super.onCreate()

        initKoin {
            androidContext(this@App)
            androidLogger(level = Level.INFO) //Change it on release build to Level.NONE
            modules(viewModelModule, validatorModule, userDataStoreRepositoryModule)
        }
    }
}