package com.prep.android.restaurantapp.model

import com.google.gson.annotations.SerializedName

data class RestaurantBrief(
    @SerializedName("id") val id: Int,
    @SerializedName("name")val name: String,
    @SerializedName("description")val description: String,
    @SerializedName("cover_img_url")val coverImgUrl: String,
    @SerializedName("status")val status: String,
    @SerializedName("delivery_fee")val deliveryFee: Int
)
