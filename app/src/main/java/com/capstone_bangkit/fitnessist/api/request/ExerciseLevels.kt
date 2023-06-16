package com.capstone_bangkit.fitnessist.api.request


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep
import com.google.gson.annotations.Expose

@Keep
data class ExerciseLevels(
    @SerializedName("calories_burned")
    @Expose
    val caloriesBurned: Int?,
    @SerializedName("duration")
    @Expose
    val duration: Int?,
    @SerializedName("exercise_id")
    @Expose
    val exerciseId: String?,
    @SerializedName("id")
    @Expose
    val id: String?,
    @SerializedName("level")
    @Expose
    val level: String?,
    @SerializedName("repetition")
    @Expose
    val repetition: Int?,
    @SerializedName("sets")
    @Expose
    val sets: Int?
)