package com.prep.android.restaurantapp.model

import com.google.gson.annotations.SerializedName

data class RestaurantInfo (
    @SerializedName("name") val name: String,
    @SerializedName("yelp_rating") val yelpRating: Double,
    @SerializedName("yelp_review_count") val yelpReviewCount: Int,
    @SerializedName("phone_number") val phoneNumber: String,
    @SerializedName("description") val description: String,
    @SerializedName("cover_img_url")val coverImgUrl: String
)