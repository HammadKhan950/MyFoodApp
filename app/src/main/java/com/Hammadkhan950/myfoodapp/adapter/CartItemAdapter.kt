package com.Hammadkhan950.myfoodapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.Hammadkhan950.myfoodapp.R
import com.Hammadkhan950.myfoodapp.model.FoodItem

internal class CartItemAdapter(
    private val cartList: ArrayList<FoodItem>,
    val context: Context
) : RecyclerView.Adapter<CartItemAdapter.CartViewHolder>() {


    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): CartViewHolder {
        val itemView =
            LayoutInflater.from(p0.context).inflate(R.layout.single_cart_item_details, p0, false)
        return CartViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return cartList.size
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    override fun onBindViewHolder(p0: CartViewHolder, p1: Int) {
        val cartObject = cartList[p1]
        p0.itemName.text = cartObject.foodName
        val cost = "Rs. ${cartObject.foodCostForOne}"
        p0.itemCost.text = cost
    }
    class CartViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val itemName: TextView = view.findViewById(R.id.txtCartItemName)
        val itemCost: TextView = view.findViewById(R.id.txtCartPrice)
    }


}