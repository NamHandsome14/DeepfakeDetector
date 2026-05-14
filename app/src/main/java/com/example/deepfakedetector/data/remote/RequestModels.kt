package com.example.deepfakedetector.data.remote

import com.google.gson.annotations.SerializedName

data class DetectionRequest(
    @SerializedName("image_base64")
    val imageBase64: String,
    @SerializedName("model_version")
    val modelVersion: String = "1.0.0"
)

data class BatchDetectionRequest(
    @SerializedName("images")
    val images: List<String>,
    @SerializedName("model_version")
    val modelVersion: String = "1.0.0"
)

