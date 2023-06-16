package com.capstone_bangkit.fitnessist.api

data class PostTDEECalculationRequest(
    val gender: String,
    val age: Int,
    val weight: Int,
    val height: Int,
    val activity: String,
    val fat: Double,
    val program_id: String
)
