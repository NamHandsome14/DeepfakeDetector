package com.example.deepfakedetector.data.local

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.deepfakedetector.data.model.HistoryItem

class HistoryDatabaseHelper(context: Context) : SQLiteOpenHelper(
    context,
    DATABASE_NAME,
    null,
    DATABASE_VERSION
) {
    companion object {
        private const val DATABASE_NAME = "deepfake_history.db"
        private const val DATABASE_VERSION = 1
        private const val TABLE_NAME = "history"
        private const val COLUMN_ID = "id"
        private const val COLUMN_IMAGE_URI = "image_uri"
        private const val COLUMN_LABEL = "label"
        private const val COLUMN_CONFIDENCE = "confidence_score"
        private const val COLUMN_TIMESTAMP = "timestamp"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val createTableSQL = """
            CREATE TABLE $TABLE_NAME (
                $COLUMN_ID TEXT PRIMARY KEY,
                $COLUMN_IMAGE_URI TEXT NOT NULL,
                $COLUMN_LABEL TEXT NOT NULL,
                $COLUMN_CONFIDENCE REAL NOT NULL,
                $COLUMN_TIMESTAMP LONG NOT NULL
            )
        """.trimIndent()
        db?.execSQL(createTableSQL)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        // TODO: Handle database upgrades
    }

    fun addHistoryItem(item: HistoryItem) {
        val db = writableDatabase
        val values = android.content.ContentValues().apply {
            put(COLUMN_ID, item.id)
            put(COLUMN_IMAGE_URI, item.imageUri)
            put(COLUMN_LABEL, item.label)
            put(COLUMN_CONFIDENCE, item.confidenceScore)
            put(COLUMN_TIMESTAMP, item.timestamp)
        }
        db.insert(TABLE_NAME, null, values)
    }

    fun getAllHistory(): List<HistoryItem> {
        val db = readableDatabase
        val cursor = db.query(TABLE_NAME, null, null, null, null, null, "$COLUMN_TIMESTAMP DESC")
        val items = mutableListOf<HistoryItem>()

        cursor.use {
            while (it.moveToNext()) {
                items.add(
                    HistoryItem(
                        id = it.getString(it.getColumnIndexOrThrow(COLUMN_ID)),
                        imageUri = it.getString(it.getColumnIndexOrThrow(COLUMN_IMAGE_URI)),
                        label = it.getString(it.getColumnIndexOrThrow(COLUMN_LABEL)),
                        confidenceScore = it.getDouble(it.getColumnIndexOrThrow(COLUMN_CONFIDENCE)),
                        timestamp = it.getLong(it.getColumnIndexOrThrow(COLUMN_TIMESTAMP))
                    )
                )
            }
        }
        return items
    }

    fun deleteHistoryItem(id: String) {
        val db = writableDatabase
        db.delete(TABLE_NAME, "$COLUMN_ID = ?", arrayOf(id))
    }

    fun clearHistory() {
        val db = writableDatabase
        db.delete(TABLE_NAME, null, null)
    }

    override fun close() {
        super.close()
    }
}

