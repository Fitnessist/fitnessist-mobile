package com.capstone_bangkit.fitnessist.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.capstone_bangkit.fitnessist.databinding.ActivitySuccessAddFoodBinding

class SuccessAddFoodActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySuccessAddFoodBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySuccessAddFoodBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}