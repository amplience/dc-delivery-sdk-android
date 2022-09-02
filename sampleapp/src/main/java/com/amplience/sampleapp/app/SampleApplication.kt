package com.amplience.sampleapp.app

import android.app.Application
import com.amplience.ampliencesdk.BuildConfig
import com.amplience.ampliencesdk.ContentClient
import timber.log.Timber

class SampleApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}
