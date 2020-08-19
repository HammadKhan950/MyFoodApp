package com.Hammadkhan950.myfoodapp.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "dishes")
data class DishEntity(
    @PrimaryKey val id: String,
    @ColumnInfo(name = "dish_name") val dishName: String,
    @ColumnInfo(name = "dish_rating") val dishRating: String,
    @ColumnInfo(name = "dish_cost_for_one") val dishCostForOne: String,
    @ColumnInfo(name = "dish_image") val dishImage: String
)