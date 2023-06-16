package com.capstone_bangkit.fitnessist.model.workouts

import android.os.Parcel
import android.os.Parcelable
import androidx.annotation.Keep
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

@Keep
data class ExerciseLevel(
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
    val sets: Int?,

    @SerializedName("points")
    @Expose
    val points: Int?
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readValue(Int::class.java.classLoader) as? Int
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeValue(caloriesBurned)
        parcel.writeValue(duration)
        parcel.writeString(exerciseId)
        parcel.writeString(id)
        parcel.writeString(level)
        parcel.writeValue(repetition)
        parcel.writeValue(sets)
        parcel.writeValue(points)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ExerciseLevel> {
        override fun createFromParcel(parcel: Parcel): ExerciseLevel {
            return ExerciseLevel(parcel)
        }

        override fun newArray(size: Int): Array<ExerciseLevel?> {
            return arrayOfNulls(size)
        }
    }
}
