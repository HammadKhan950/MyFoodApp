package com.Hammadkhan950.myfoodapp.model

import org.json.JSONArray

data class OrderHistory (
    val orderId:Int,
    val restaurantName:String,
    val orderDate:String,
    val foodItems:JSONArray
)