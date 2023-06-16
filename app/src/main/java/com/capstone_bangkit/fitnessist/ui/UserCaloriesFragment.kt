package com.capstone_bangkit.fitnessist.ui

import android.app.DatePickerDialog
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
import java.util.Calendar

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
            kebutuhanKalori.text = getCalories

            btnScanFood.setOnClickListener {
                val scanFood = Intent(context, InstructionCameraScanActivity::class.java)
                startActivity(scanFood)
            }

            btnAddFood.setOnClickListener {
                val addFood = Intent(context, AddFoodActivity::class.java)
                startActivity(addFood)
            }

            rvFoodHistory.layoutManager = LinearLayoutManager(requireContext())
            binding.rvFoodHistory.adapter = foodHistoryAdapter
        }


        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(requireContext(), { view, selectedYear, selectedMonth, selectedDay ->
            val selectedDate = String.format("%02d-%02d-%d", selectedDay, selectedMonth + 1, selectedYear)
        }, year, month, day)

        datePickerDialog.show()

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