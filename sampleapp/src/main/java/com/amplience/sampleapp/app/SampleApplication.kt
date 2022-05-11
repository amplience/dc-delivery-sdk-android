package com.amplience.sampleapp.app

import android.app.Application
import com.amplience.ampliencesdk.AmplienceManager
import com.amplience.ampliencesdk.BuildConfig
import com.amplience.ampliencesdk.api.models.FilterRequest
import timber.log.Timber

class SampleApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        AmplienceManager.initialise(applicationContext, "ampproduct-doc")
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}
