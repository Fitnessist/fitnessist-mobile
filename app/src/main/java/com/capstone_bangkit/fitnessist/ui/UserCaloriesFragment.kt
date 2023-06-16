package com.capstone_bangkit.fitnessist.ui

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.capstone_bangkit.fitnessist.adapter.FoodHistoryAdapter
import com.capstone_bangkit.fitnessist.api.ApiConfig
import com.capstone_bangkit.fitnessist.api.GetFoodHistoryResponse
import com.capstone_bangkit.fitnessist.authentication.AuthenticationManager
import com.capstone_bangkit.fitnessist.databinding.FragmentUserCaloriesBinding
import retrofit2.Call
import retrofit2.Response
import retrofit2.Callback

class UserCaloriesFragment : Fragment() {
    private lateinit var authentication: AuthenticationManager
    private lateinit var foodHistoryAdapter: FoodHistoryAdapter
    private lateinit var binding: FragmentUserCaloriesBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        authentication = AuthenticationManager(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentUserCaloriesBinding.inflate(inflater, container, false)

        foodHistoryAdapter = FoodHistoryAdapter(mutableListOf())

        val getCalories = authentication.getAccessInt(AuthenticationManager.CALORIES_EACH_DAY_TARGET).toString()

        binding.apply {
            tvKebutuhanKalori.text = getCalories

            btnScanFood.setOnClickListener {
                val scanFood = Intent(context, InstructionCameraScanActivity::class.java)
                startActivity(scanFood)
            }

            btnAddFood.setOnClickListener {
                val addFood = Intent(context, AddFoodManualActivity::class.java)
                startActivity(addFood)
            }

            rvFoodHistory.layoutManager = LinearLayoutManager(requireContext())
            binding.rvFoodHistory.adapter = foodHistoryAdapter
        }

        val getToken = authentication.getAccess(AuthenticationManager.TOKEN).toString()
        val token = "Bearer $getToken"
        val date = "16-06-2023"
        ApiConfig.getApiService().getFoodHistory(token, date).enqueue(object : Callback<GetFoodHistoryResponse> {
            override fun onResponse(
                call: Call<GetFoodHistoryResponse>,
                response: Response<GetFoodHistoryResponse>
            ) {
                if (response.isSuccessful) {
                    val foodHistoryResponse = response.body()
                    val foodHistoryList = foodHistoryResponse?.data ?: emptyList()
                    foodHistoryAdapter.setData(foodHistoryList)
                    val totalCalories = foodHistoryList.sumByDouble { it.total_calories ?: 0.0 }
                    binding.tvKaloriMakanan.text = totalCalories.toInt().toString()
                    binding.tvKaloriSisa.text = (getCalories.toInt() - totalCalories).toInt().toString()
                } else {
                    // Handle error response
                }
            }

            override fun onFailure(call: Call<GetFoodHistoryResponse>, t: Throwable) {
                //
            }
        })

        return binding.root
    }
}