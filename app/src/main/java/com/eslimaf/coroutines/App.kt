package com.eslimaf.coroutines

import android.app.Application
import android.util.Log
import com.squareup.picasso.Picasso

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        buildPicasso()
    }

    private fun buildPicasso() {
        Log.i("APP_INIT", "Building Picasso")
        val builder = Picasso.Builder(this)
            .indicatorsEnabled(true)
            .build()
        Picasso.setSingletonInstance(builder)
    }
}