package com.prep.android.restaurantapp.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.prep.android.restaurantapp.R
import com.prep.android.restaurantapp.model.RestaurantBrief
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_restaurant.view.*

class RestaurantListAdaptor(val onRestaurntClick: (Int) -> Unit) : RecyclerView.Adapter<RestaurantListAdaptor.RestaurantViewHolder>() {
    private var items = emptyList<RestaurantBrief>()

    fun updateList(newItems: List<RestaurantBrief>) {
        items = newItems
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: RestaurantViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RestaurantViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_restaurant, parent, false)
        return RestaurantViewHolder(view)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    inner class RestaurantViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(data: RestaurantBrief) {
            with(itemView) {
                Picasso.get().load(data.coverImgUrl).into(imageCover)
                text_item_res_name.text = data.name
                text_item_food_type.text = data.description
                text_item_status.text = data.status
                itemView.setOnClickListener {
                    onRestaurntClick(data.id)
                }
            }
        }
    }

}