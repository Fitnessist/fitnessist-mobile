package com.capstone_bangkit.fitnessist.api.request


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep
import com.google.gson.annotations.Expose

@Keep
data class Exercise(
    @SerializedName("exercise_levels")
    @Expose
    val exerciseLevels: List<Any?>?,
    @SerializedName("id")
    @Expose
    val id: String?,
    @SerializedName("name")
    @Expose
    val name: String?
)