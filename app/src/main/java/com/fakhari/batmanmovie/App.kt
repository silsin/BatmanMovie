package com.fakhari.batmanmovie

import android.app.Activity
import android.app.Application
import android.content.Context
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.ProcessLifecycleOwner
import com.fakhari.batmanmovie.data.database.MovieDatabase
import com.fakhari.batmanmovie.di.AppModule
import com.fakhari.batmanmovie.di.allModules
import dagger.hilt.android.HiltAndroidApp
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

@HiltAndroidApp
class App : Application(), LifecycleObserver {

    companion object {
        lateinit var context: Context
        var activity: Activity? = null
        var db : MovieDatabase? = null
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
    }
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(applicationContext)
            modules(allModules)
        }
        ProcessLifecycleOwner.get().lifecycle.addObserver(this)
         db = AppModule.createDatabase(this)
    }



}