package com.example.deepfakedetector.ui.result

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.deepfakedetector.R

class ResultActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        try {
            setContentView(R.layout.activity_result)

            // Retrieve detection result from intent
            val confidenceScore = intent.getDoubleExtra("confidence_score", 0.0)
            val resultLabel = intent.getStringExtra("result_label") ?: "Unknown"

            // Display result
            setupUI(confidenceScore, resultLabel)
        } catch (e: Exception) {
            e.printStackTrace()
            android.util.Log.e("ResultActivity", "Error in onCreate: ${e.message}", e)
            finish()
        }
    }

    private fun setupUI(confidenceScore: Double, resultLabel: String) {
        try {
            val textViewResultLabel = findViewById<TextView>(R.id.textViewResultLabel)
            val textViewConfidenceResult = findViewById<TextView>(R.id.textViewConfidenceResult)
            val progressBarResult = findViewById<android.widget.ProgressBar>(R.id.progressBarResult)
            
            if (textViewResultLabel != null && textViewConfidenceResult != null && progressBarResult != null) {
                textViewResultLabel.text = "Result: $resultLabel"
                textViewConfidenceResult.text = "Confidence: ${String.format("%.2f%%", confidenceScore * 100)}"
                progressBarResult.progress = (confidenceScore * 100).toInt()
                
                // UI testing mode - buttons for testing
                val buttonAnalyzeAgain = findViewById<Button>(R.id.buttonAnalyzeAgain)
                buttonAnalyzeAgain?.setOnClickListener {
                    finish()
                }
                
                val buttonSaveResult = findViewById<Button>(R.id.buttonSaveResult)
                buttonSaveResult?.setOnClickListener {
                    // Save disabled for UI testing
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
            android.util.Log.e("ResultActivity", "Error in setupUI: ${e.message}", e)
        }
    }
}

