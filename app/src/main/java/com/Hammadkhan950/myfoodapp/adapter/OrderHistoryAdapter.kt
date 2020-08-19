package com.Hammadkhan950.myfoodapp.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.Hammadkhan950.myfoodapp.R
import com.Hammadkhan950.myfoodapp.model.FoodItem
import com.Hammadkhan950.myfoodapp.model.OrderHistory
import java.text.SimpleDateFormat

class OrderHistoryAdapter(val context: Context, val orderHistoryList: ArrayList<OrderHistory>) :
    RecyclerView.Adapter<OrderHistoryAdapter.OrderHistoryViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderHistoryViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.single_item_history, parent, false)
        return OrderHistoryViewHolder(view)
    }

    override fun getItemCount(): Int {
        return orderHistoryList.size

    }

    override fun onBindViewHolder(holder: OrderHistoryViewHolder, position: Int) {
        val orderHistory = orderHistoryList[position]
        val restaurantName = "Rest. name:${orderHistory.restaurantName}"
        holder.txtRestaurantName.text = restaurantName


        val sdfSource = SimpleDateFormat("dd-MM-yy HH:mm:ss")
        val date = sdfSource.parse(orderHistory.orderDate)
        val sdfDestination = SimpleDateFormat("dd-MM-yyyy")
        val orderDate = "Order date:${sdfDestination.format(date)}"
        holder.txtOrderDate.text = orderDate


        val foodItemList = ArrayList<FoodItem>()
        for (i in 0 until orderHistory.foodItems.length()) {
            val foodJson = orderHistory.foodItems.getJSONObject(i)
            foodItemList.add(
                FoodItem(
                    foodJson.getString("food_item_id"),
                    foodJson.getString("name"),
                    foodJson.getString("cost").toInt()
                )
            )
        }
        val cartItemAdapter = CartItemAdapter(foodItemList, context)
        val layoutManager = LinearLayoutManager(context)
        holder.singleFoodCostRecyclerView.layoutManager = layoutManager
        holder.singleFoodCostRecyclerView.adapter = cartItemAdapter


    }
    class OrderHistoryViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val txtRestaurantName: TextView = view.findViewById(R.id.txtRestaurantName)
        val txtOrderDate: TextView = view.findViewById(R.id.txtOrderDate)
        val singleFoodCostRecyclerView: RecyclerView = view.findViewById(R.id.singleFoodCostRecyclerView)
    }
}

