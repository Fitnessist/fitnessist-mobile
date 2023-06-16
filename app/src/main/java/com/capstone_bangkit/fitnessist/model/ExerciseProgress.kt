package com.capstone_bangkit.fitnessist.model


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep
import com.capstone_bangkit.fitnessist.model.workouts.Exercise
import com.capstone_bangkit.fitnessist.model.workouts.ExerciseLevel
import com.capstone_bangkit.fitnessist.model.workouts.Workout
import com.google.gson.annotations.Expose

@Keep
data class ExerciseProgress(
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
    val exerciseLevels: List<ExerciseLevel>?,
    @SerializedName("program")
    @Expose
    val program: Any?,
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