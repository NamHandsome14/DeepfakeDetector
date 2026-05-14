package com.example.deepfakedetector.data.model

data class DetectionResult(
    val id: String,
    val imageUri: String,
    val isFakeface: Boolean,
    val confidenceScore: Double,
    val timestamp: Long,
    val modelVersion: String
)

