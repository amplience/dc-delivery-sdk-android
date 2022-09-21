package com.amplience.sampleapp.app

import android.app.Application
import com.amplience.sampleapp.BuildConfig
import com.amplience.sdk.delivery.android.ContentClient
import timber.log.Timber

class SampleApplication : Application() {

    companion object {
    }

    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}
