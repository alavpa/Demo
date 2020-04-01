package com.alavpa.demo.android

import androidx.multidex.MultiDexApplication
import com.alavpa.demo.BuildConfig
import com.alavpa.demo.androidModule
import com.alavpa.demo.data.apiModule
import com.alavpa.demo.data.dbModule
import com.alavpa.demo.domain.domainModule
import com.alavpa.demo.presentation.presentationModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import timber.log.Timber

class DemoApplication : MultiDexApplication() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@DemoApplication)
            modules(
                listOf(
                    apiModule,
                    dbModule,
                    domainModule,
                    presentationModule,
                    androidModule
                )
            )
        }

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}
