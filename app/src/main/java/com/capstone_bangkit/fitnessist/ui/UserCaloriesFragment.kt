package com.capstone_bangkit.fitnessist.ui

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import androidx.recyclerview.widget.LinearLayoutManager
import com.capstone_bangkit.fitnessist.adapter.FoodHistoryAdapter
import com.capstone_bangkit.fitnessist.api.ApiConfig
import com.capstone_bangkit.fitnessist.api.GetFoodHistoryResponse
import com.capstone_bangkit.fitnessist.authentication.AuthenticationManager
import com.capstone_bangkit.fitnessist.databinding.FragmentUserCaloriesBinding
import retrofit2.Call
import retrofit2.Response
import retrofit2.Callback
import java.text.SimpleDateFormat
import java.util.*

class UserCaloriesFragment : Fragment() {
    private lateinit var authentication: AuthenticationManager
    private lateinit var foodHistoryAdapter: FoodHistoryAdapter
    private lateinit var binding: FragmentUserCaloriesBinding
    private var selectedDate: String = ""

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

            btnSelectDate.setOnClickListener {
                showDatePicker()
            }
        }

        val getToken = authentication.getAccess(AuthenticationManager.TOKEN).toString()
        val token = "Bearer $getToken"
        val defaultDate = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(Date())
        selectedDate = defaultDate

        binding.tvSelectedDate.text = selectedDate

        fetchDataByDate(selectedDate)

        return binding.root
    }

    private fun showDatePicker() {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(
            requireContext(),
            DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
                val selectedCalendar = Calendar.getInstance()
                selectedCalendar.set(year, month, dayOfMonth)
                val selectedDateFormat = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
                val selectedDate = selectedDateFormat.format(selectedCalendar.time)
                this.selectedDate = selectedDate
                binding.tvSelectedDate.text = selectedDate
                fetchDataByDate(selectedDate)
            },
            year,
            month,
            day
        )
        datePickerDialog.datePicker.maxDate = System.currentTimeMillis()
        datePickerDialog.show()
    }

    private fun fetchDataByDate(date: String) {
        val getCalories = authentication.getAccessInt(AuthenticationManager.CALORIES_EACH_DAY_TARGET).toString()
        val getToken = authentication.getAccess(AuthenticationManager.TOKEN).toString()
        val token = "Bearer $getToken"

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
                    foodHistoryAdapter.setData(emptyList())
                    binding.tvKaloriMakanan.text = "0"
                    binding.tvKaloriSisa.text = "0"
                }
            }

            override fun onFailure(call: Call<GetFoodHistoryResponse>, t: Throwable) {
                //
            }
        })
    }
}
