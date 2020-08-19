package com.Hammadkhan950.myfoodapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.Hammadkhan950.myfoodapp.R
import com.Hammadkhan950.myfoodapp.model.FoodItem

class DishAdapter(
    val context: Context,
    val foodItemList: List<FoodItem>,
    val listener: OnItemClickListener
) :
    RecyclerView.Adapter<DishAdapter.DishViewHolder>() {

    companion object {
        var isCartEmpty = true
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DishViewHolder {

        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.single_dish_item, parent, false)
        return DishViewHolder(view)
    }

    override fun getItemCount(): Int {

        return foodItemList.size
    }

    interface OnItemClickListener {
        fun onAddItemClick(foodItem: FoodItem)
        fun onRemoveItemClick(foodItem: FoodItem)
    }

    override fun onBindViewHolder(holder: DishViewHolder, position: Int) {
        val dishdetails = foodItemList[position]
        holder.txtDishName.text = dishdetails.foodName
        val cost="Rs. ${dishdetails.foodCostForOne}"
        holder.txtDishPrice.text = cost
        holder.txtSerialNumber.text = (position+1).toString()

        holder.btnSelect.setOnClickListener {
            holder.btnSelect.visibility = View.GONE
            holder.btnDeselect.visibility = View.VISIBLE
            listener.onAddItemClick(dishdetails)
        }
        holder.btnDeselect.setOnClickListener {
            holder.btnDeselect.visibility = View.GONE
            holder.btnSelect.visibility = View.VISIBLE
            listener.onRemoveItemClick(dishdetails)

        }
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    class DishViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val txtDishName: TextView = view.findViewById(R.id.txtDishName)
        val txtDishPrice: TextView = view.findViewById(R.id.txtDishPrice)
        val btnSelect: Button = view.findViewById(R.id.btnSelect)
        val txtSerialNumber: TextView = view.findViewById(R.id.txtSerialNumber)
        val btnDeselect: Button = view.findViewById(R.id.btnDeSelect)
    }
}