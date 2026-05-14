package com.example.deepfakedetector.ui.history

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.deepfakedetector.R
import com.example.deepfakedetector.adapter.HistoryAdapter
import com.example.deepfakedetector.data.local.HistoryDatabaseHelper

class HistoryActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var historyAdapter: HistoryAdapter
    private var databaseHelper: HistoryDatabaseHelper? = null  // Disabled for UI testing

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        try {
            setContentView(R.layout.activity_history)

            // Database disabled for UI testing
            // databaseHelper = HistoryDatabaseHelper(this)
            setupRecyclerView()
            loadHistory()
        } catch (e: Exception) {
            e.printStackTrace()
            android.util.Log.e("HistoryActivity", "Error in onCreate: ${e.message}", e)
            finish()
        }
    }

    private fun setupRecyclerView() {
        try {
            recyclerView = findViewById(R.id.recyclerViewHistory)
            recyclerView.layoutManager = LinearLayoutManager(this)
            historyAdapter = HistoryAdapter(mutableListOf())
            recyclerView.adapter = historyAdapter
        } catch (e: Exception) {
            e.printStackTrace()
            android.util.Log.e("HistoryActivity", "Error in setupRecyclerView: ${e.message}", e)
        }
    }

    private fun loadHistory() {
        try {
            // Database disabled for UI testing - show empty list
            historyAdapter.updateItems(emptyList())
        } catch (e: Exception) {
            e.printStackTrace()
            android.util.Log.e("HistoryActivity", "Error in loadHistory: ${e.message}", e)
        }
    }
}

