package com.capstone_bangkit.fitnessist.api.request


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep
import com.google.gson.annotations.Expose

@Keep
data class Workout(
    @SerializedName("day")
    @Expose
    val day: Int?,
    @SerializedName("id")
    @Expose
    val id: String?,
    @SerializedName("programId")
    @Expose
    val programId: String?
)