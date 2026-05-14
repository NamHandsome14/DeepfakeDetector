package com.example.deepfakedetector.data.local

import android.content.Context
import android.content.SharedPreferences
import android.util.Log

class PreferenceManager(context: Context) {
    companion object {
        private const val PREF_NAME = "deepfake_detector_prefs"
        private const val KEY_FIRST_LAUNCH = "first_launch"
        private const val KEY_MODEL_VERSION = "model_version"
        private const val KEY_LAST_DETECTION_TIME = "last_detection_time"
        private const val KEY_TOTAL_DETECTIONS = "total_detections"
    }

    private val sharedPreferences: SharedPreferences = try {
        context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE).also {
            Log.d("PreferenceManager", "SharedPreferences initialized")
        }
    } catch (e: Exception) {
        Log.e("PreferenceManager", "Error initializing SharedPreferences: ${e.message}", e)
        throw e
    }

    fun isFirstLaunch(): Boolean {
        return try {
            sharedPreferences.getBoolean(KEY_FIRST_LAUNCH, true)
        } catch (e: Exception) {
            Log.e("PreferenceManager", "Error in isFirstLaunch: ${e.message}", e)
            true
        }
    }

    fun setFirstLaunchFalse() {
        try {
            sharedPreferences.edit().putBoolean(KEY_FIRST_LAUNCH, false).apply()
        } catch (e: Exception) {
            Log.e("PreferenceManager", "Error in setFirstLaunchFalse: ${e.message}", e)
        }
    }

    fun getModelVersion(): String {
        return try {
            sharedPreferences.getString(KEY_MODEL_VERSION, "1.0.0") ?: "1.0.0"
        } catch (e: Exception) {
            Log.e("PreferenceManager", "Error in getModelVersion: ${e.message}", e)
            "1.0.0"
        }
    }

    fun setModelVersion(version: String) {
        try {
            sharedPreferences.edit().putString(KEY_MODEL_VERSION, version).apply()
        } catch (e: Exception) {
            Log.e("PreferenceManager", "Error in setModelVersion: ${e.message}", e)
        }
    }

    fun getLastDetectionTime(): Long {
        return try {
            sharedPreferences.getLong(KEY_LAST_DETECTION_TIME, 0)
        } catch (e: Exception) {
            Log.e("PreferenceManager", "Error in getLastDetectionTime: ${e.message}", e)
            0
        }
    }

    fun setLastDetectionTime(timestamp: Long) {
        try {
            sharedPreferences.edit().putLong(KEY_LAST_DETECTION_TIME, timestamp).apply()
        } catch (e: Exception) {
            Log.e("PreferenceManager", "Error in setLastDetectionTime: ${e.message}", e)
        }
    }

    fun getTotalDetections(): Int {
        return try {
            sharedPreferences.getInt(KEY_TOTAL_DETECTIONS, 0)
        } catch (e: Exception) {
            Log.e("PreferenceManager", "Error in getTotalDetections: ${e.message}", e)
            0
        }
    }

    fun incrementTotalDetections() {
        try {
            val current = getTotalDetections()
            sharedPreferences.edit().putInt(KEY_TOTAL_DETECTIONS, current + 1).apply()
        } catch (e: Exception) {
            Log.e("PreferenceManager", "Error in incrementTotalDetections: ${e.message}", e)
        }
    }

    fun clearPreferences() {
        try {
            sharedPreferences.edit().clear().apply()
        } catch (e: Exception) {
            Log.e("PreferenceManager", "Error in clearPreferences: ${e.message}", e)
        }
    }
}

