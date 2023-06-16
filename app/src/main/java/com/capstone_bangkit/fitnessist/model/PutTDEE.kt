package com.capstone_bangkit.fitnessist.model

data class PutTDEE(
    val id: String,
    val gender: String,
    val age: Int,
    val weight: Int,
    val height: Int,
    val activity: String,
    val fat: Int,
    val user_id: String,
    val weight_target: Int,
    val calories_each_day: Int,
    val calories_each_day_target: Int
)
