package com.example.deepfakedetector.data.remote

import com.google.gson.annotations.SerializedName

data class DetectionResponse(
    @SerializedName("is_deepfake")
    val isDeepfake: Boolean,
    @SerializedName("confidence_score")
    val confidenceScore: Double,
    @SerializedName("processing_time_ms")
    val processingTimeMs: Long,
    @SerializedName("model_version")
    val modelVersion: String
)

data class BatchDetectionResponse(
    @SerializedName("results")
    val results: List<DetectionResponse>,
    @SerializedName("total_processing_time_ms")
    val totalProcessingTimeMs: Long
)

