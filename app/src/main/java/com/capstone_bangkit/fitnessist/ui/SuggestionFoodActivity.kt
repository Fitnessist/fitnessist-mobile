package com.capstone_bangkit.fitnessist.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.capstone_bangkit.fitnessist.adapter.SnapFoodAdapter
import com.capstone_bangkit.fitnessist.databinding.ActivitySuggestionFoodBinding
import com.capstone_bangkit.fitnessist.model.FoodPrediction

class SuggestionFoodActivity : AppCompatActivity() {
    private lateinit var snapFoodAdapter: SnapFoodAdapter
    private lateinit var binding: ActivitySuggestionFoodBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySuggestionFoodBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val foodPredictions = intent.getParcelableArrayListExtra<FoodPrediction>("foodPredictions")
        val foodPredictionsList = foodPredictions?.toList()

        val layoutManager = LinearLayoutManager(this)
        binding.rvSugesstionFood.layoutManager = layoutManager
        snapFoodAdapter = foodPredictionsList?.let { SnapFoodAdapter(it) }!!
        binding.rvSugesstionFood.adapter = snapFoodAdapter
    }
}