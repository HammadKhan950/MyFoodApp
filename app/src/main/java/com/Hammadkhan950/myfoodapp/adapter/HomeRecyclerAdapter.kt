package com.Hammadkhan950.myfoodapp.adapter

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.Hammadkhan950.myfoodapp.R
import com.Hammadkhan950.myfoodapp.activity.DashboardActivity
import com.Hammadkhan950.myfoodapp.database.DishEntity
import com.Hammadkhan950.myfoodapp.fragment.DishFragment
import com.Hammadkhan950.myfoodapp.fragment.HomeFragment
import com.Hammadkhan950.myfoodapp.fragment.HomeFragment.DbAsyncTask
import com.Hammadkhan950.myfoodapp.fragment.OTPResetFragment
import com.Hammadkhan950.myfoodapp.model.Dish

import com.squareup.picasso.Picasso


class HomeRecyclerAdapter(val context: Context, val itemList: ArrayList<Dish>) :
    RecyclerView.Adapter<HomeRecyclerAdapter.HomeViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.recycler_home_single_item, parent, false)
        return HomeViewHolder(view)
    }
    override fun getItemCount(): Int {
        return itemList.size
    }
    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
        val dish = itemList[position]
        holder.txtDishesName.text = dish.dishName
        holder.txtDishPrice.text = dish.dishCostForOne
        holder.txtDishRating.text = dish.dishRating
        Picasso.get().load(dish.dishImage).error(R.drawable.order).into(holder.imgDishImage)
        holder.llContent.setOnClickListener {
            val dishFragment= DishFragment()
            val args= Bundle()
            args.putString("restaurant_id",dish.dishId)
            args.putString("restaurant_name",dish.dishName)
            dishFragment.arguments=args
            val transaction =
                (context as DashboardActivity).supportFragmentManager.beginTransaction()
            transaction.replace(R.id.frameLayout, dishFragment)
            transaction.commit()
        }
        val dishEntity = DishEntity(
            dish.dishId,
            dish.dishName,
            dish.dishCostForOne,
            dish.dishRating,
            dish.dishImage
        )
        val checkFav = DbAsyncTask(context, dishEntity, 1).execute()
        val isFav = checkFav.get()
        if (isFav) {
            holder.imgFavDishIcon.setImageResource(R.drawable.ic_full_rating)

        }
        holder.imgFavDishIcon.setOnClickListener {
            if (!DbAsyncTask(context, dishEntity, 1).execute().get()) {
                val async = DbAsyncTask(context, dishEntity, 2).execute()
                val result = async.get()
                if (result) {
                    holder.imgFavDishIcon.setImageResource(R.drawable.ic_full_rating)
                    Toast.makeText(context, "Restaurant added to favourites", Toast.LENGTH_LONG).show()

                } else {
                    Toast.makeText(context, "Some error occurred!", Toast.LENGTH_LONG).show()
                }

            } else {
                val async = DbAsyncTask(context, dishEntity, 3).execute()
                val result = async.get()
                if (result) {
                    holder.imgFavDishIcon.setImageResource(R.drawable.ic_empty_rating)
                    Toast.makeText(context, "Restaurant removed from favourites", Toast.LENGTH_LONG)
                        .show()

                } else {
                    Toast.makeText(context, "Some error occurred", Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    class HomeViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val txtDishesName: TextView = view.findViewById(R.id.txtDishesName)
        val txtDishPrice: TextView = view.findViewById(R.id.txtDishPrice)
        val imgDishImage: ImageView = view.findViewById(R.id.imgDishImage)
        val txtDishRating: TextView = view.findViewById(R.id.txtDishRating)
        val imgFavDishIcon: ImageView = view.findViewById(R.id.txtDishIcon)
        val llContent: LinearLayout = view.findViewById(R.id.llContent)
    }

}