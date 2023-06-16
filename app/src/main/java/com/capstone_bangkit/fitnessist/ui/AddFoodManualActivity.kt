package com.capstone_bangkit.fitnessist.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.capstone_bangkit.fitnessist.databinding.ActivityAddFoodManualBinding

class AddFoodManualActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddFoodManualBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddFoodManualBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.back.setOnClickListener {
            onBackPressed()
        }

        var foodId = intent.getStringExtra("food_id")
        var foodName = intent.getStringExtra("food_name")
        var imageUrl = intent.getStringExtra("image_url")
        var caloriesPer100gr = intent.getStringExtra("calories_per_100gr")

        binding.tvFoodName.text = foodName
        binding.btnTambahkan.setOnClickListener {
            val intent = Intent(this@AddFoodManualActivity, FoodCaloriesReviewActivity::class.java)
            intent.putExtra("food_id", foodId)
            intent.putExtra("food_name", foodName)
            intent.putExtra("image_url", imageUrl)
            intent.putExtra("calories_per_100gr", caloriesPer100gr)
            intent.putExtra("total_grams", binding.edtFoodWeight.text.toString())
            startActivity(intent)
        }
    }
}