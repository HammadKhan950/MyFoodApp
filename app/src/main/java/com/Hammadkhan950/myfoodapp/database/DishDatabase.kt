package com.Hammadkhan950.myfoodapp.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [DishEntity::class, FoodEntity::class], version = 1)
abstract class DishDatabase : RoomDatabase() {

    abstract fun dishDao(): DishDao
    abstract fun foodDao(): FoodDao
}