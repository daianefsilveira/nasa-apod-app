package br.com.nasaapodapi

import android.app.Application
import br.com.nasaapodapi.di.appModules
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class AppApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@AppApplication)
            modules(appModules)
        }
    }
}