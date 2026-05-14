package com.example.deepfakedetector.utils

object Constants {
    // App
    const val APP_NAME = "DeepFake Detector"
    const val APP_VERSION = "1.0.0"

    // API
    const val API_BASE_URL = "https://api.deepfakedetector.com"
    const val API_TIMEOUT_SECONDS = 30L

    // Model
    const val MODEL_NAME = "model_deepfake.tflite"
    const val MODEL_VERSION = "1.0.0"
    const val INPUT_IMAGE_SIZE = 224
    const val CONFIDENCE_THRESHOLD = 0.5f

    // Database
    const val DATABASE_NAME = "deepfake_history.db"
    const val DATABASE_VERSION = 1

    // UI
    const val ANIMATION_DURATION_MS = 300
    const val SPLASH_DURATION_MS = 2000

    // File sizes (in MB)
    const val MAX_IMAGE_SIZE_MB = 50.0
    const val MAX_CACHE_SIZE_MB = 100.0

    // Activity Request Codes
    const val REQUEST_IMAGE_CAPTURE = 1
    const val REQUEST_GALLERY_IMAGE = 2
}

