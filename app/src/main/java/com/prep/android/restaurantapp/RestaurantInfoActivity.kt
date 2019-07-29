package com.prep.android.restaurantapp

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.prep.android.restaurantapp.model.RestaurantInfo
import com.prep.android.restaurantapp.model.RestaurantViewModel
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_info.*

class RestaurantInfoActivity: AppCompatActivity(){

    private lateinit var viewModel: RestaurantViewModel
    private var restaurantId = 0

    private lateinit var contentViews: List<View>

    companion object {
        const val EXTRA_ID_KEY = "com.prep.android.restaurantapp.restaurantinfoactivity.id"
        fun newIntentWithId(id: Int, context: Context): Intent {
            val intent = Intent(context, RestaurantInfoActivity::class.java)
            intent.putExtra(EXTRA_ID_KEY, id)
            return intent
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_info)
        restaurantId = intent.getIntExtra(EXTRA_ID_KEY, 0)
        collectContentViews()
        setupViewModel()
    }

    private fun collectContentViews() {
        contentViews = listOf(
            image_detail_cover,
            text_res_name,
            text_phone_num,
            text_yelp_rating,
            text_yelp_count,
            text_description,
            text_header_yelp_rating,
            text_header_yelp_count,
            text_header_phone
        )
    }

    private fun setupViewModel() {
        viewModel = ViewModelProviders.of(this).get(RestaurantViewModel::class.java)
        fetchDataWithId()
        viewModel.getRestaurantInfo().observe(this, Observer<RestaurantInfo> { info ->
            setupUIContent(info)
        })
    }

    private fun setupUIContent(info: RestaurantInfo) {
        text_res_name.text = info.name
        text_phone_num.text = info.phoneNumber
        if (info.yelpReviewCount > 0) {
            text_yelp_rating.text = info.yelpRating.toString()
            text_yelp_count.text = info.yelpReviewCount.toString()
        } else {
            text_yelp_rating.text = getText(R.string.rating_not_available)
            text_yelp_count.text = ""
        }
        text_description.text = info.description
        Picasso.get().load(info.coverImgUrl).into(image_detail_cover)
        finishLoading()
    }

    private fun fetchDataWithId() {
        viewModel.fetchRestaurantInfo(restaurantId)
    }

    private fun finishLoading() {
        contentViews.forEach {view ->
            view.visibility = View.VISIBLE
        }
        progressBar.visibility = View.GONE
    }
}