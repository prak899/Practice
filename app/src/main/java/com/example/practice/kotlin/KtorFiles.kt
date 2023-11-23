package com.example.practice.kotlin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.practice.R
import com.example.practice.databinding.ActivityKtorFilesBinding

class KtorFiles : AppCompatActivity() {
    private lateinit var ktorFiles: ActivityKtorFilesBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ktorFiles = ActivityKtorFilesBinding.inflate(layoutInflater)
        setContentView(ktorFiles.root)

        ktorFiles.switchImage.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                // Switch is ON
                // Do something when the switch is checked
                ktorFiles.imageView3.setImageResource(R.drawable.baseline_delete_outline_24)
            } else {
                // Switch is OFF
                // Do something when the switch is unchecked
            }
        }
    }
}