package com.capstone_bangkit.fitnessist.model.workouts


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep
import com.capstone_bangkit.fitnessist.model.User
import com.google.gson.annotations.Expose

@Keep
data class MyExerciseProgress(
    @SerializedName("exercise")
    @Expose
    val exercise: Exercise?,

    @SerializedName("exercise_id")
    @Expose
    val exerciseId: String?,

    @SerializedName("exercise_level_id")
    @Expose
    val exerciseLevelId: String?,

    @SerializedName("exercise_levels")
    @Expose
    val exerciseLevels: ExerciseLevels?,

    @SerializedName("program")
    @Expose
    val program: Program?,

    @SerializedName("program_id")
    @Expose
    val programId: String?,

    @SerializedName("user")
    @Expose
    val user: User?,

    @SerializedName("workout")
    @Expose
    val workout: Workout?,

    @SerializedName("workout_id")
    @Expose
    val workoutId: String?
)