package com.example.deepfakedetector.ui.main

import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.google.android.material.button.MaterialButton
import com.example.deepfakedetector.R
import com.example.deepfakedetector.data.model.DetectionResult
import com.example.deepfakedetector.repository.DetectionRepository
import com.example.deepfakedetector.ui.history.HistoryActivity
import com.example.deepfakedetector.ui.result.ResultActivity
import com.example.deepfakedetector.utils.ImageUtils
import com.example.deepfakedetector.utils.PermissionUtils
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var repository: DetectionRepository

    private lateinit var imageViewPreview: ImageView
    private lateinit var imageViewLogo: ImageView
    private lateinit var buttonCamera: MaterialButton
    private lateinit var buttonGallery: MaterialButton
    private lateinit var buttonDetect: MaterialButton
    private lateinit var buttonHistory: MaterialButton
    private lateinit var progressBar: ProgressBar
    private lateinit var textViewStatus: TextView
    private lateinit var textViewPlaceholder: TextView

    private var selectedImageUri: Uri? = null
    private var capturedBitmap: Bitmap? = null

    private val galleryLauncher =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
            uri?.let {
                selectedImageUri = it
                capturedBitmap = null
                imageViewPreview.setImageURI(it)
                imageViewPreview.visibility = View.VISIBLE
                textViewPlaceholder.visibility = View.GONE
                textViewStatus.text = getString(R.string.image_selected)
            }
        }

    private val cameraLauncher =
        registerForActivityResult(ActivityResultContracts.TakePicturePreview()) { bitmap: Bitmap? ->
            bitmap?.let {
                capturedBitmap = it
                selectedImageUri = null
                imageViewPreview.setImageBitmap(it)
                imageViewPreview.visibility = View.VISIBLE
                textViewPlaceholder.visibility = View.GONE
                textViewStatus.text = getString(R.string.image_captured)
            }
        }

    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
            val allGranted = permissions.all { it.value }
            if (allGranted) {
                // All permissions granted, proceed with the operation
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        try {
            setContentView(R.layout.activity_main)

            repository = DetectionRepository(this)

            initViews()
            setupListeners()
            setupInitialUI()

            // Request permissions on app start
            checkAndRequestPermissions()
        } catch (e: Exception) {
            e.printStackTrace()
            android.util.Log.e("MainActivity", "Error in onCreate: ${e.message}", e)
            finish()
        }
    }

    private fun initViews() {
        try {
            imageViewLogo = findViewById(R.id.imageViewLogo)
            imageViewPreview = findViewById(R.id.imageViewPreview)
            buttonCamera = findViewById(R.id.buttonCamera)
            buttonGallery = findViewById(R.id.buttonGallery)
            buttonDetect = findViewById(R.id.buttonDetect)
            buttonHistory = findViewById(R.id.buttonHistory)
            progressBar = findViewById(R.id.progressBar)
            textViewStatus = findViewById(R.id.textViewStatus)
            textViewPlaceholder = findViewById(R.id.textViewPlaceholder)

            android.util.Log.d("MainActivity", "All views initialized successfully")
        } catch (e: Exception) {
            e.printStackTrace()
            android.util.Log.e("MainActivity", "Error initializing views: ${e.message}", e)
            throw e
        }
    }

    private fun setupInitialUI() {
        try {
            progressBar.visibility = View.GONE
            imageViewPreview.visibility = View.VISIBLE
            textViewPlaceholder.visibility = View.VISIBLE
            textViewStatus.text = getString(R.string.status_ready)
            android.util.Log.d("MainActivity", "Initial UI setup completed")
        } catch (e: Exception) {
            android.util.Log.e("MainActivity", "Error in setupInitialUI: ${e.message}", e)
        }
    }

    private fun setupListeners() {
        buttonCamera.setOnClickListener {
            launchCamera()
        }

        buttonGallery.setOnClickListener {
            launchGallery()
        }

        buttonDetect.setOnClickListener {
            detectDeepfake()
        }

        buttonHistory.setOnClickListener {
            openHistory()
        }
    }

    private fun checkAndRequestPermissions() {
        val missingPermissions = PermissionUtils.getMissingPermissions(this)
        if (missingPermissions.isNotEmpty()) {
            requestPermissionLauncher.launch(missingPermissions)
        }
    }

    private fun launchCamera() {
        cameraLauncher.launch(null)
    }

    private fun launchGallery() {
        galleryLauncher.launch("image/*")
    }

    private fun openHistory() {
        startActivity(Intent(this, HistoryActivity::class.java))
    }

    private fun detectDeepfake() {
        val uri = selectedImageUri
        val bmp = capturedBitmap

        if (uri == null && bmp == null) {
            textViewStatus.text = getString(R.string.status_select_or_capture)
            return
        }

        lifecycleScope.launch {
            try {
                showLoading(true)
                textViewStatus.text = getString(R.string.status_detecting)

                val bitmapToAnalyze = uri?.let {
                    ImageUtils.loadBitmapFromUri(this@MainActivity, it)
                } ?: bmp

                if (bitmapToAnalyze == null) {
                    showLoading(false)
                    textViewStatus.text = getString(R.string.status_failed_to_load)
                    return@launch
                }

                val result = repository.detectDeepfake(bitmapToAnalyze)
                showLoading(false)
                navigateToResult(result)

            } catch (e: Exception) {
                showLoading(false)
                android.util.Log.e("MainActivity", "Error detecting deepfake: ${e.message}", e)
                textViewStatus.text = getString(R.string.status_error, e.message ?: "Unknown error")
            }
        }
    }

    private fun showLoading(isLoading: Boolean) {
        progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        buttonDetect.isEnabled = !isLoading
        buttonCamera.isEnabled = !isLoading
        buttonGallery.isEnabled = !isLoading
        buttonHistory.isEnabled = !isLoading
    }

    private fun navigateToResult(result: DetectionResult) {
        val intent = Intent(this, ResultActivity::class.java).apply {
            putExtra("confidence_score", result.confidenceScore)
            putExtra("result_label", if (result.isFakeface) "Deepfake" else "Real")
        }
        startActivity(intent)
    }

    override fun onDestroy() {
        super.onDestroy()
        repository.close()
    }
}