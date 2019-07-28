package com.prep.android.restaurantapp

import android.os.Build
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.RecyclerView
import com.prep.android.restaurantapp.model.RestaurantBrief
import com.prep.android.restaurantapp.view.RestaurantListAdaptor
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(
    RobolectricTestRunner::class
)
@Config(sdk = [Build.VERSION_CODES.O_MR1])
class MainActivityTest {

    @Test
    fun setupToolBarTest() {
        val activity = Robolectric.buildActivity(MainActivity::class.java!!).create().start().resume().get()
        assertEquals((activity.findViewById<Toolbar>(R.id.toolbar)).title, "Discover")
    }

    @Test
    fun recylcerViewTest() {
        val activity = Robolectric.buildActivity(MainActivity::class.java!!).create().start().resume().get()
        val rv = activity.findViewById<RecyclerView>(R.id.rv_list)
        val adaptor = rv.adapter as RestaurantListAdaptor
        adaptor.updateList(arrayListOf<RestaurantBrief>().apply {
            for (i in 1..5) {
                add(RestaurantBrief(i, "", "", "", "", i))
            }
        })
        assertEquals(5, rv.layoutManager!!.itemCount)
    }
}