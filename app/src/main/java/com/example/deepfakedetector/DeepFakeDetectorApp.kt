package com.example.deepfakedetector

import android.app.Application
import android.util.Log

class DeepFakeDetectorApp : Application() {

    override fun onCreate() {
        super.onCreate()

        // Set up global uncaught exception handler
        val defaultHandler = Thread.getDefaultUncaughtExceptionHandler()
        Thread.setDefaultUncaughtExceptionHandler { thread, throwable ->
            Log.e("DeepFakeDetectorApp", "Uncaught exception in thread ${thread.name}", throwable)
            throwable.printStackTrace()

            // Call the default handler
            defaultHandler?.uncaughtException(thread, throwable)
        }

        Log.d("DeepFakeDetectorApp", "Application initialized")
    }
}

