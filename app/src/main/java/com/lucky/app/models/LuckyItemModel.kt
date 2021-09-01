package com.lucky.app.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class LuckyItemModel(
    @SerializedName("detailUrl") val detailUrl: String? = "",
    @SerializedName("imageUrl") val imageUrl: String? = "",
    @SerializedName("brand") val brand: String? = "",
    @SerializedName("title") val title: String? = "",
    @SerializedName("tags") val tags: String? = "",
    @SerializedName("favoriteCount") val favoriteCount: Int = 0
) : Parcelable