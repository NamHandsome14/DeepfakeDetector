package com.example.deepfakedetector.ui.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.example.deepfakedetector.R
import com.example.deepfakedetector.ui.main.MainActivity

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        try {
            // Gắn giao diện cho Splash Screen
            setContentView(R.layout.activity_splash)

            // Chuyển sang MainActivity sau 2.5 giây
            Handler(Looper.getMainLooper()).postDelayed({
                try {
                    startActivity(Intent(this, MainActivity::class.java))
                    finish()
                } catch (e: Exception) {
                    e.printStackTrace()
                    android.util.Log.e("SplashActivity", "Error starting MainActivity: ${e.message}", e)
                    finish()
                }
            }, 2500)
        } catch (e: Exception) {
            e.printStackTrace()
            android.util.Log.e("SplashActivity", "Error in onCreate: ${e.message}", e)
            finish()
        }
    }
}