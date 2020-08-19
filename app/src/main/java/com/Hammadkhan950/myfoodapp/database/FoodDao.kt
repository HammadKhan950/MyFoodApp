package com.Hammadkhan950.myfoodapp.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface FoodDao {
    @Insert
    fun insertFood(foodEntity: FoodEntity)

    @Delete
    fun deleteFood(foodEntity: FoodEntity)

    @Query("SELECT * FROM food_item")
    fun getAllFood(): List<FoodEntity>

    @Query("DELETE FROM food_item WHERE resID=:resId")
    fun deleteOrders(resId:String)

    @Query("DELETE FROM food_item")
    fun nukeTable()
}