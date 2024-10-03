package com.example.songhub

import android.app.Application
import com.example.songhub.di.appModule
import com.example.songhub.di.storageModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class MySongsApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger(Level.DEBUG)
            androidContext(this@MySongsApplication)
            modules (
                appModule,
                storageModule
            )
        }
    }
}