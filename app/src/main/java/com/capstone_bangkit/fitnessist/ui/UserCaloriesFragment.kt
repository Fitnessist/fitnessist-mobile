package com.capstone_bangkit.fitnessist.ui

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.capstone_bangkit.fitnessist.adapter.FoodHistoryAdapter
import com.capstone_bangkit.fitnessist.authentication.AuthenticationManager
import com.capstone_bangkit.fitnessist.databinding.FragmentUserCaloriesBinding

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
        binding.btnScanFood.setOnClickListener {
            val scanFood = Intent(context, InstructionCameraScanActivity::class.java)
            startActivity(scanFood)
        }
        binding.btnAddFood.setOnClickListener {
            val addFood = Intent(context, AddFoodActivity::class.java)
            startActivity(addFood)
        }
        val getCalories = authentication.getAccessInt(AuthenticationManager.CALORIES_EACH_DAY_TARGET).toString()
        binding.kebutuhanKalori.text = getCalories
        return binding.root
    }
}