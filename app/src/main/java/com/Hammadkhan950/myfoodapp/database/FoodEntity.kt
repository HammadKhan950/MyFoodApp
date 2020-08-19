package com.Hammadkhan950.myfoodapp.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "food_item")
data class FoodEntity(
    @PrimaryKey val resID: String,
    @ColumnInfo(name = "food_items") val foodItems: String

)