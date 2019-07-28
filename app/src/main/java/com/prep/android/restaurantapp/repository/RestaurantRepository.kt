package com.prep.android.restaurantapp.repository

import androidx.lifecycle.MutableLiveData
import com.prep.android.restaurantapp.model.RestaurantBrief
import com.prep.android.restaurantapp.network.RestaurantApi
import com.prep.android.restaurantapp.network.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

private const val NUMBER_PER_FETCH = 50
private const val NUMBER_MAX_CACHE = 300

object RestaurantRepository {
    private var api = RetrofitInstance.createService(RestaurantApi::class.java)
    private val restaurantLiveData = MutableLiveData<List<RestaurantBrief>>()

    private var cacheList = arrayListOf<RestaurantBrief>()
    private var cacheOffset = 0

    fun getRestaurantLiveData(): MutableLiveData<List<RestaurantBrief>> {
        return restaurantLiveData
    }

    fun fetchMoreRestaurants(lat: Double, lng: Double) {
        api.getRestaurants(lat, lng, cacheOffset, NUMBER_PER_FETCH).enqueue(object : Callback<List<RestaurantBrief>> {
            override fun onResponse(call: Call<List<RestaurantBrief>>, response: Response<List<RestaurantBrief>>) {
                if (response.isSuccessful && response.body() != null) {
                    saveToCache(response.body()!!)
//                    refactorCache()
                    restaurantLiveData.postValue(cacheList)
                }
            }

            override fun onFailure(call: Call<List<RestaurantBrief>>, t: Throwable) {
                restaurantLiveData.postValue(cacheList)
            }
        })
    }

    fun resetCache() {
        cacheList.clear()
        cacheOffset = 0
        restaurantLiveData.postValue(listOf())
    }

    fun getCacheSize(): Int {
        return cacheList.size
    }

    private fun saveToCache(newData: List<RestaurantBrief>) {
        cacheList.addAll(newData)
        // cacheOffset could be simply cacheList.size in this case, however this line is for fixed-Sized cache
        // in the future
        cacheOffset += newData.size
    }

    // Current implemetation does not limit the size of the cache, but we should in reality!!
    // Need also update the RestaurantOnScrollListener for this functionality.
    private fun refactorCache() {
        if (cacheList.size > NUMBER_MAX_CACHE) {
            cacheList = ArrayList(cacheList.subList(cacheList.size - NUMBER_MAX_CACHE, cacheList.size))
        }
    }

}