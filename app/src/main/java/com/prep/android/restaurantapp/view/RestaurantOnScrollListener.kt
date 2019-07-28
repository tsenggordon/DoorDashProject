package com.prep.android.restaurantapp.view

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

private const val PRELOAD_THRESHOLD = 5

class RestaurantOnScrollListener(
    private val layoutManager: LinearLayoutManager,
    private val loadMoreDataCallback: () -> Unit
) : RecyclerView.OnScrollListener() {
    private var startLoading = true
    private var previousItemCount = 0
    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        val itemCount = layoutManager.itemCount
        val lastVisibleIdx = layoutManager.findLastVisibleItemPosition()
        if (startLoading && (itemCount > previousItemCount)) {
            startLoading = false
            previousItemCount = itemCount
        }

        if ((lastVisibleIdx + PRELOAD_THRESHOLD) >= itemCount && !startLoading) {
            startLoading = true
            loadMoreDataCallback()
        }
    }

}