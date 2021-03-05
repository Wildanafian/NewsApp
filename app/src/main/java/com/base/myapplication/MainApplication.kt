package com.base.myapplication

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import com.base.myapplication.di.apiModule
import com.base.myapplication.di.networkModule
import com.base.myapplication.di.repoModule
import com.base.myapplication.di.viewModel

class MainApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@MainApplication)
            modules(
                apiModule,
                networkModule,
                repoModule,
                viewModel
            )
        }
    }
}