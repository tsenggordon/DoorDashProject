package com.prep.android.restaurantapp.Util

import androidx.lifecycle.MutableLiveData

internal fun <T> MutableLiveData<List<T>>.add(newItems: List<T>) {
    this.value = this.value?.toMutableList()?.apply {
        addAll(newItems)
    }
}