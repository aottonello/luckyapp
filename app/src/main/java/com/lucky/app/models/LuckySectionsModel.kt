package com.lucky.app.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class LuckySectionsModel(
    @SerializedName("title") val title: String? = "",
    @SerializedName("items") val items: List<LuckyItemModel>? = emptyList()
) : Parcelable