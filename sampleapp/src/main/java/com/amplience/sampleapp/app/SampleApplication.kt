package com.amplience.sampleapp.app

import android.app.Application
import com.amplience.sampleapp.BuildConfig
import timber.log.Timber

class SampleApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}
