package com.lucky.app.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class LuckyItemDetailModel(
    @SerializedName("imageUrl") val imageUrl: String? = "",
    @SerializedName("brand") val brand: String? = "",
    @SerializedName("title") val title: String? = "",
    @SerializedName("description") val description: String? = "",
    @SerializedName("date") val date: String? = "",
    @SerializedName("favoriteCount") val favoriteCount: Int = 0,
    @SerializedName("redemption") val redemption: String? = "",
    @SerializedName("labelPrice") val labelPrice: String? = "",
    @SerializedName("price") val price: String? = "",
    @SerializedName("priceDiscount") val priceDiscount: String? = ""
) : Parcelable