package com.dicoding.myreportapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.google.firebase.crashlytics.FirebaseCrashlytics
import timber.log.Timber

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Timber.tag("MainActivity: ").d("Test debug log")

        val btnCrash = findViewById<Button>(R.id.button)
        btnCrash.setOnClickListener {
            FirebaseCrashlytics.getInstance().log("Clicked on button")
            FirebaseCrashlytics.getInstance().setCustomKey("str_key", "some_data")
            try {
                throw RuntimeException("This is a test crash")
            } catch (e: Exception) {
                Timber.e("Test non fatal exception")
            }
        }
    }
}