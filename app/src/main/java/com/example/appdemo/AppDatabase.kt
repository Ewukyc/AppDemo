package com.example.appdemo

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [FavoriteCourse::class], version = 1)
abstract class AppDatabase() : RoomDatabase() {
    abstract fun favoriteDao(): FavoriteDao
}