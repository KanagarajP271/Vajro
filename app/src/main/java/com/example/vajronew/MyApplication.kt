package com.example.vajronew

import android.app.Application
import android.content.Context

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        //  instance = this;
        context = applicationContext
    }

    companion object {
        val instance: MyApplication? = null

        //  return instance.getApplicationContext();
        var context: Context? = null
            private set
    }
}