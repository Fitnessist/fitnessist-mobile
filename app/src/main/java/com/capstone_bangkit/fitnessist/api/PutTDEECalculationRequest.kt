package com.capstone_bangkit.fitnessist.api

data class PutTDEECalculationRequest(
    val gender: String,
    val age: Int,
    val weight: Int,
    val height: Int,
    val activity: String,
    val fat: Double,
    val program_id: String,
    val weight_target: Int
)
