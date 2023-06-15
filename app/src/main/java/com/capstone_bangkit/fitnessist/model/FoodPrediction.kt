package com.capstone_bangkit.fitnessist.model

import android.os.Parcel
import android.os.Parcelable

data class FoodPrediction(
    val confidence_percentage: Double,
    val food: Food?
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readDouble(),
        parcel.readParcelable(Food::class.java.classLoader)
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeDouble(confidence_percentage)
        parcel.writeParcelable(food, flags)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<FoodPrediction> {
        override fun createFromParcel(parcel: Parcel): FoodPrediction {
            return FoodPrediction(parcel)
        }

        override fun newArray(size: Int): Array<FoodPrediction?> {
            return arrayOfNulls(size)
        }
    }
}
