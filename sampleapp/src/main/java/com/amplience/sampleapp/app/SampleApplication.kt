package com.amplience.sampleapp.app

import android.app.Application
import com.amplience.ampliencesdk.ContentClient
import com.amplience.ampliencesdk.BuildConfig
import timber.log.Timber

class SampleApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        ContentClient.initialise(applicationContext, "ampproduct-doc")
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}
