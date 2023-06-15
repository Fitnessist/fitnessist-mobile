package com.capstone_bangkit.fitnessist.model

data class FoodItem(
    val foodName: String,
    val calories: Int,
    val imageUrl: String,
    val confidencePercentage: Double
)
