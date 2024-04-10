package com.urb.composeapp

import android.app.Application
import com.urb.composeapp.home.di.realmModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class EmployeesApp : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()

            androidContext(this@EmployeesApp)

            modules(realmModule)
        }
    }
}