package com.example.deepfakedetector.repository

import android.content.Context
import android.graphics.Bitmap
import android.util.Log
import com.example.deepfakedetector.data.local.HistoryDatabaseHelper
import com.example.deepfakedetector.data.local.PreferenceManager
import com.example.deepfakedetector.data.model.DetectionResult
import com.example.deepfakedetector.data.model.HistoryItem
import com.example.deepfakedetector.ml.TFLiteClassifier
import java.util.UUID

class DetectionRepository(context: Context) {
    private val classifier: TFLiteClassifier
    private val databaseHelper: HistoryDatabaseHelper? = null  // Disabled for UI testing
    private val preferenceManager: PreferenceManager

    init {
        try {
            classifier = TFLiteClassifier(context)
            preferenceManager = PreferenceManager(context)
            Log.d("DetectionRepository", "Repository initialized successfully")
        } catch (e: Exception) {
            Log.e("DetectionRepository", "Error initializing repository: ${e.message}", e)
            throw e
        }
    }

    suspend fun detectDeepfake(bitmap: Bitmap, modelVersion: String = "1.0.0"): DetectionResult {
        return try {
            val result = classifier.classifyImage(bitmap)
            val isFake = result.first
            val confidence = result.second

            val detectionResult = DetectionResult(
                id = UUID.randomUUID().toString(),
                imageUri = "",
                isFakeface = isFake,
                confidenceScore = confidence,
                timestamp = System.currentTimeMillis(),
                modelVersion = modelVersion
            )

            // Database saving disabled for UI testing
            preferenceManager.incrementTotalDetections()
            preferenceManager.setLastDetectionTime(System.currentTimeMillis())

            Log.d("DetectionRepository", "Detection completed: $detectionResult")
            detectionResult
        } catch (e: Exception) {
            Log.e("DetectionRepository", "Error in detectDeepfake: ${e.message}", e)
            // Return default result if detection fails
            DetectionResult(
                id = UUID.randomUUID().toString(),
                imageUri = "",
                isFakeface = false,
                confidenceScore = 0.5,
                timestamp = System.currentTimeMillis(),
                modelVersion = modelVersion
            )
        }
    }

    fun saveToHistory(result: DetectionResult) {
        if (databaseHelper != null) {
            try {
                val historyItem = HistoryItem(
                    id = result.id,
                    imageUri = result.imageUri,
                    label = if (result.isFakeface) "Deepfake" else "Real",
                    confidenceScore = result.confidenceScore,
                    timestamp = result.timestamp
                )
                databaseHelper.addHistoryItem(historyItem)
            } catch (e: Exception) {
                Log.e("DetectionRepository", "Error saving to history: ${e.message}", e)
            }
        }
    }

    fun getHistory(): List<HistoryItem> {
        return try {
            databaseHelper?.getAllHistory() ?: emptyList()
        } catch (e: Exception) {
            Log.e("DetectionRepository", "Error getting history: ${e.message}", e)
            emptyList()
        }
    }

    fun deleteHistoryItem(id: String) {
        try {
            databaseHelper?.deleteHistoryItem(id)
        } catch (e: Exception) {
            Log.e("DetectionRepository", "Error deleting history item: ${e.message}", e)
        }
    }

    fun clearHistory() {
        try {
            databaseHelper?.clearHistory()
        } catch (e: Exception) {
            Log.e("DetectionRepository", "Error clearing history: ${e.message}", e)
        }
    }

    fun close() {
        try {
            classifier.close()
            databaseHelper?.close()
            Log.d("DetectionRepository", "Repository closed successfully")
        } catch (e: Exception) {
            Log.e("DetectionRepository", "Error closing repository: ${e.message}", e)
        }
    }
}
