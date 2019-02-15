package com.demoss.newsline.application

import android.app.Application
import org.koin.android.ext.android.startKoin

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        initKoin()
    }

    private fun initKoin() {
        startKoin(this, applicationModule)
    }
}