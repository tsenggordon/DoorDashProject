package com.prep.android.restaurantapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.prep.android.restaurantapp.model.RestaurantBrief
import com.prep.android.restaurantapp.model.RestaurantViewModel
import com.prep.android.restaurantapp.view.RestaurantListAdaptor
import com.prep.android.restaurantapp.view.RestaurantOnScrollListener
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: RestaurantViewModel

    private val layoutManager = LinearLayoutManager(this)
    private val rvAdaptor = RestaurantListAdaptor()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupUI()
        setupViewModel()

    }

    private fun setupUI() {
        setupRecycleView()
        setupToolBar()
    }

    private fun setupRecycleView() {
        rv_list.layoutManager = layoutManager
        rv_list.adapter = rvAdaptor
        rv_list.addItemDecoration(DividerItemDecoration(this, layoutManager.orientation))
        rv_list.addOnScrollListener(RestaurantOnScrollListener(layoutManager, ::fetchData))
    }

    private fun setupToolBar() {
        toolbar.title = getString(R.string.tool_bar_title)
        toolbar.setTitleTextColor(getColor(R.color.white))
    }

    private fun setupViewModel() {
        viewModel = ViewModelProviders.of(this).get(RestaurantViewModel::class.java)
        viewModel.clearData()
        fetchData()
        viewModel.getRestaurantBriefs().observe(this, Observer<List<RestaurantBrief>> { listOfRestaurants ->
            rvAdaptor.updateList(listOfRestaurants)
        })
    }

    private fun fetchData() {
        viewModel.fetchMoreRestaurant()
    }
}
