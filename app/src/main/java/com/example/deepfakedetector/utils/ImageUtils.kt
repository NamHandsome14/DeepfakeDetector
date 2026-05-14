package com.example.deepfakedetector.utils

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.content.Context
import android.util.Log
import java.io.InputStream

object ImageUtils {
    const val MAX_WIDTH = 1024
    const val MAX_HEIGHT = 1024

    fun loadBitmapFromUri(context: Context, uri: Uri): Bitmap? {
        return try {
            val inputStream = context.contentResolver.openInputStream(uri)
            inputStream?.use { BitmapFactory.decodeStream(it) }.also {
                Log.d("ImageUtils", "Successfully loaded bitmap from URI")
            }
        } catch (e: Exception) {
            Log.e("ImageUtils", "Error loading bitmap from URI: ${e.message}", e)
            e.printStackTrace()
            null
        }
    }

    fun scaleBitmap(bitmap: Bitmap, maxWidth: Int = MAX_WIDTH, maxHeight: Int = MAX_HEIGHT): Bitmap {
        return try {
            val width = bitmap.width
            val height = bitmap.height

            if (width <= maxWidth && height <= maxHeight) {
                return bitmap
            }

            val aspectRatio = width.toFloat() / height.toFloat()
            val newWidth: Int
            val newHeight: Int

            if (width > height) {
                newWidth = maxWidth
                newHeight = (maxWidth / aspectRatio).toInt()
            } else {
                newHeight = maxHeight
                newWidth = (maxHeight * aspectRatio).toInt()
            }

            Bitmap.createScaledBitmap(bitmap, newWidth, newHeight, true).also {
                Log.d("ImageUtils", "Bitmap scaled to ${newWidth}x${newHeight}")
            }
        } catch (e: Exception) {
            Log.e("ImageUtils", "Error scaling bitmap: ${e.message}", e)
            bitmap
        }
    }

    fun compressBitmap(bitmap: Bitmap, quality: Int = 80): Bitmap {
        return try {
            val stream = java.io.ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.JPEG, quality, stream)
            val bytes = stream.toByteArray()
            BitmapFactory.decodeByteArray(bytes, 0, bytes.size).also {
                Log.d("ImageUtils", "Bitmap compressed successfully")
            }
        } catch (e: Exception) {
            Log.e("ImageUtils", "Error compressing bitmap: ${e.message}", e)
            bitmap
        }
    }
}

