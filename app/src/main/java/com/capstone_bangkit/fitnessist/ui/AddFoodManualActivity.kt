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
import com.capstone_bangkit.fitnessist.databinding.ActivityAddFoodManualBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AddFoodManualActivity : AppCompatActivity() {
    private lateinit var authentication: AuthenticationManager
    private lateinit var binding: ActivityAddFoodManualBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddFoodManualBinding.inflate(layoutInflater)
        setContentView(binding.root)

        authentication = AuthenticationManager(this)

        binding.apply {
            back.setOnClickListener {
                onBackPressed()
            }

            val getToken = authentication.getAccess(AuthenticationManager.TOKEN).toString()
            val token = "Bearer $getToken"

            btnTambahkan.setOnClickListener {
                var foodName = edtFoodName.text.toString()
                var totalCalories = edtFoodWeight.text.toString().toDouble()
                val request = AddFoodHistoryRequest(
                    food_name = foodName,
                    food_id = "",
                    image_url = "",
                    calories_per_100gr = 0.0,
                    total_grams = 0,
                    total_calories = totalCalories
                )
                ApiConfig.getApiService().addFoodHistory(token, request).enqueue(object:
                    Callback<AddFoodHistoryResponse> {
                    override fun onResponse(
                        call: Call<AddFoodHistoryResponse>,
                        response: Response<AddFoodHistoryResponse>
                    ) {
                        if (response.isSuccessful) {
                            val responseBody = response.body()
                            val intent = Intent(this@AddFoodManualActivity, MainActivity::class.java)
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