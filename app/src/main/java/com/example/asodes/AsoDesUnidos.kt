package com.example.asodes

import android.app.Application
import android.content.Context

class AsoDesUnidos : Application() {
    companion object {
        lateinit var appContext: Context
    }

    override fun onCreate() {
        super.onCreate()
        appContext = applicationContext
    }
}