package com.example.deepfakedetector.utils

import android.content.Context
import android.os.Environment
import java.io.File

object FileUtils {
    fun getCacheDir(context: Context): File {
        return context.cacheDir
    }

    fun getTempImageFile(context: Context): File {
        val storageDir = File(context.cacheDir, "temp_images")
        if (!storageDir.exists()) {
            storageDir.mkdirs()
        }
        return File(storageDir, "temp_image_${System.currentTimeMillis()}.jpg")
    }

    fun getAppStorageDir(context: Context, dirName: String): File {
        val storageDir = File(context.getExternalFilesDir(null), dirName)
        if (!storageDir.exists()) {
            storageDir.mkdirs()
        }
        return storageDir
    }

    fun deleteFile(file: File): Boolean {
        return file.delete()
    }

    fun clearDirectory(directory: File) {
        if (directory.isDirectory) {
            directory.listFiles()?.forEach { file ->
                if (file.isDirectory) {
                    clearDirectory(file)
                } else {
                    file.delete()
                }
            }
        }
    }

    fun getFileSizeInMB(file: File): Double {
        return file.length() / (1024.0 * 1024.0)
    }
}

