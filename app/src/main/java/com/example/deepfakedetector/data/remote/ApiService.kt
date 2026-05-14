package com.example.deepfakedetector.data.remote

import retrofit2.http.POST
import retrofit2.http.Body
import retrofit2.Call

interface ApiService {
    @POST("/api/detect")
    fun detectDeepfake(@Body request: DetectionRequest): Call<DetectionResponse>

    @POST("/api/batch-detect")
    fun batchDetect(@Body request: BatchDetectionRequest): Call<BatchDetectionResponse>
}

