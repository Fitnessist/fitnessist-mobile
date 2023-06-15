package com.capstone_bangkit.fitnessist.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.capstone_bangkit.fitnessist.MainActivity
import com.capstone_bangkit.fitnessist.R
import com.capstone_bangkit.fitnessist.api.AddFoodHistoryRequest
import com.capstone_bangkit.fitnessist.api.AddFoodHistoryResponse
import com.capstone_bangkit.fitnessist.api.ApiConfig
import com.capstone_bangkit.fitnessist.authentication.AuthenticationManager
import com.capstone_bangkit.fitnessist.databinding.ActivityFoodCaloriesReviewBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FoodCaloriesReviewActivity : AppCompatActivity() {
    private lateinit var authentication: AuthenticationManager
    private lateinit var binding: ActivityFoodCaloriesReviewBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFoodCaloriesReviewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        authentication = AuthenticationManager(this)

        binding.btnBack.setOnClickListener {
            onBackPressed()
        }

        var foodId = intent.getStringExtra("food_id")
        var foodName = intent.getStringExtra("food_name")
        var imageUrl = intent.getStringExtra("image_url")
        var caloriesPer100gr = intent.getStringExtra("calories_per_100gr")?.toDouble()
        var totalGrams = intent.getStringExtra("total_grams")?.toDouble()

        val totalCalories = (caloriesPer100gr!! / 100) * totalGrams!!

        binding.apply {
            tvFoodName.text = foodName
            tvCaloriesPer100gr.text = caloriesPer100gr.toString()
            tvTotalFoodWeight.text = totalGrams.toString()
            tvTotalCalories.text = totalCalories.toString()

            val getToken = authentication.getAccess(AuthenticationManager.TOKEN).toString()
            val token = "Bearer $getToken"
            val request = AddFoodHistoryRequest(
                food_name = foodName!!,
                food_id = foodId!!,
                image_url = imageUrl!!,
                calories_per_100gr = totalCalories,
                total_grams = totalGrams.toInt(),
                total_calories = totalCalories
            )
            btnKonfirmasi.setOnClickListener {
                ApiConfig.getApiService().addFoodHistory(token, request).enqueue(object:
                    Callback<AddFoodHistoryResponse> {
                    override fun onResponse(
                        call: Call<AddFoodHistoryResponse>,
                        response: Response<AddFoodHistoryResponse>
                    ) {
                        if (response.isSuccessful) {
                            val responseBody = response.body()
                            val intent = Intent(this@FoodCaloriesReviewActivity, MainActivity::class.java)
                            intent.putExtra("SnapFoodSuccess", R.id.userCaloriesFragment)
                            startActivity(intent)
                            finishAffinity()
                        } else {
                            val errorBody = response.errorBody()
                        }
                    }

                    override fun onFailure(call: Call<AddFoodHistoryResponse>, t: Throwable) {
                        TODO("Not yet implemented")
                    }

                })
            }
        }
    }
}