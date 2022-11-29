package com.vyzyo.vyzoschoolkt.di.app

import android.app.Application
import android.content.Context
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class HiltApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        HiltApplication.appContext = applicationContext
    }

    companion object {

        lateinit  var appContext: Context

    }
}