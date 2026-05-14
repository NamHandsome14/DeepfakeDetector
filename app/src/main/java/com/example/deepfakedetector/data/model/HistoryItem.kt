package com.example.deepfakedetector.data.model

data class HistoryItem(
    val id: String,
    val imageUri: String,
    val label: String,
    val confidenceScore: Double,
    val timestamp: Long
)

