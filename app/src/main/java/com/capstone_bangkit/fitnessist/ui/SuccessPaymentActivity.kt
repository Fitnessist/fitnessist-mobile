package com.capstone_bangkit.fitnessist.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.capstone_bangkit.fitnessist.databinding.ActivitySuccessPaymentBinding

class SuccessPaymentActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySuccessPaymentBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySuccessPaymentBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}