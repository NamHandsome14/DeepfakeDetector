package com.example.deepfakedetector.ml

object ModelConstants {
    const val MODEL_FILE_NAME = "model_deepfake.tflite"
    const val INPUT_SIZE = 224
    const val CONFIDENCE_THRESHOLD = 0.5f
    const val MODEL_VERSION = "1.0.0"

    // Normalization constants
    const val NORMALIZATION_MEAN = 127.5f
    const val NORMALIZATION_STD = 127.5f

    // Class indices
    const val CLASS_REAL = 0
    const val CLASS_FAKE = 1
}

