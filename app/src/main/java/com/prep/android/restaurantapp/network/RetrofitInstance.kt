package com.prep.android.restaurantapp.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val BASE_URL = "https://api.doordash.com"

object RetrofitInstance {
    private val retrofit = Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build()
    fun <T> createService(clazz: Class<T>):T {
        return retrofit.create(clazz)
    }
}