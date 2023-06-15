package com.capstone_bangkit.fitnessist.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.capstone_bangkit.fitnessist.databinding.ActivityInstructionCameraScanBinding

class InstructionCameraScanActivity : AppCompatActivity() {
    private lateinit var binding: ActivityInstructionCameraScanBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInstructionCameraScanBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnScanFood.setOnClickListener {
            val upload = Intent(this@InstructionCameraScanActivity, UploadActivity::class.java)
            startActivity(upload)
        }
        binding.back.setOnClickListener {
            onBackPressed()
        }
    }
}