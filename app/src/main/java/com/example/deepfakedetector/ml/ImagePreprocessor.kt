package com.example.deepfakedetector.ml

import android.graphics.Bitmap
import android.graphics.Matrix
import android.util.Log

class ImagePreprocessor {
    companion object {
        private const val INPUT_WIDTH = 224
        private const val INPUT_HEIGHT = 224
    }

    fun preprocessImage(bitmap: Bitmap): Bitmap {
        return try {
            // Resize image to model input size
            val resized = Bitmap.createScaledBitmap(bitmap, INPUT_WIDTH, INPUT_HEIGHT, true)
            Log.d("ImagePreprocessor", "Image preprocessing completed: ${INPUT_WIDTH}x${INPUT_HEIGHT}")
            resized
        } catch (e: Exception) {
            Log.e("ImagePreprocessor", "Error preprocessing image: ${e.message}", e)
            // Return original bitmap if preprocessing fails
            bitmap
        }
    }

    fun cropToSquare(bitmap: Bitmap): Bitmap {
        return try {
            val size = if (bitmap.width < bitmap.height) bitmap.width else bitmap.height
            val x = (bitmap.width - size) / 2
            val y = (bitmap.height - size) / 2
            Bitmap.createBitmap(bitmap, x, y, size, size)
        } catch (e: Exception) {
            Log.e("ImagePreprocessor", "Error cropping image: ${e.message}", e)
            bitmap
        }
    }

    fun rotateBitmap(bitmap: Bitmap, degrees: Float): Bitmap {
        return try {
            val matrix = Matrix()
            matrix.postRotate(degrees)
            Bitmap.createBitmap(bitmap, 0, 0, bitmap.width, bitmap.height, matrix, true)
        } catch (e: Exception) {
            Log.e("ImagePreprocessor", "Error rotating image: ${e.message}", e)
            bitmap
        }
    }
}

