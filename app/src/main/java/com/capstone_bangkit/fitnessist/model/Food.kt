package com.capstone_bangkit.fitnessist.model

import android.os.Parcel
import android.os.Parcelable

data class Food (
    val calories_per_100gr: Int,
    val food_name: String?,
    val id: String?,
    val image_url: String?
): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(calories_per_100gr)
        parcel.writeString(food_name)
        parcel.writeString(id)
        parcel.writeString(image_url)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Food> {
        override fun createFromParcel(parcel: Parcel): Food {
            return Food(parcel)
        }

        override fun newArray(size: Int): Array<Food?> {
            return arrayOfNulls(size)
        }
    }
}