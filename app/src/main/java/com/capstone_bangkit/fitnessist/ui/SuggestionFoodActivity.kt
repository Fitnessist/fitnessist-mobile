package com.capstone_bangkit.fitnessist.ui

import android.content.Intent
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
        snapFoodAdapter.setOnItemClickCallback(object: SnapFoodAdapter.OnItemClickCallback {
            override fun onItemClicked(foodPrediction: FoodPrediction) {
                val intent = Intent(this@SuggestionFoodActivity, AddFoodActivity::class.java)
                intent.putExtra("food_id", foodPrediction.food?.id)
                intent.putExtra("food_name", foodPrediction.food?.food_name)
                intent.putExtra("image_url", foodPrediction.food?.image_url)
                intent.putExtra("calories_per_100gr", foodPrediction.food?.calories_per_100gr.toString())
            }
        })
        binding.rvSugesstionFood.adapter = snapFoodAdapter
    }
}