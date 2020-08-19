package com.Hammadkhan950.myfoodapp.adapter


import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.Hammadkhan950.myfoodapp.R
import com.Hammadkhan950.myfoodapp.activity.DashboardActivity
import com.Hammadkhan950.myfoodapp.database.DishEntity
import com.Hammadkhan950.myfoodapp.fragment.DishFragment
import com.squareup.picasso.Picasso

class FavouriteRecyclerAdapter(val context: Context, val dishList: List<DishEntity>) :
    RecyclerView.Adapter<FavouriteRecyclerAdapter.FavouriteViewHolder>() {
    class FavouriteViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val txtDishName: TextView = view.findViewById(R.id.txtDishesNameFav)
        val txtCostForOne: TextView = view.findViewById(R.id.txtDishPriceFav)
        val txtRating: TextView = view.findViewById(R.id.txtDishRatingFav)
        val imgDishImage: ImageView = view.findViewById(R.id.imgDishImageFav)
        val llContentFavourite:LinearLayout=view.findViewById(R.id.llContentFavourite)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavouriteViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.recylcler_favourite_single_item, parent, false)
        return FavouriteViewHolder(view)
    }

    override fun getItemCount(): Int {
        return dishList.size
    }

    override fun onBindViewHolder(holder: FavouriteViewHolder, position: Int) {
        val dish = dishList[position]
        holder.txtDishName.text = dish.dishName
        holder.txtCostForOne.text = dish.dishCostForOne
        holder.txtRating.text = dish.dishRating
        Picasso.get().load(dish.dishImage).error(R.drawable.order).into(holder.imgDishImage)

    }
}