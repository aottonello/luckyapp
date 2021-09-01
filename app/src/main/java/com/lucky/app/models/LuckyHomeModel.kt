package com.lucky.app.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class LuckyHomeModel(
    @SerializedName("title") val title: String? = "",
    @SerializedName("sections") val sections: List<LuckySectionsModel>? = emptyList()
) : Parcelable