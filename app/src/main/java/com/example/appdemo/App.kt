package com.example.appdemo

import android.app.Application
import androidx.room.Room

class App : Application() {
    lateinit var database: AppDatabase
    lateinit var favoriteRepo: FavoriteRepository

    override fun onCreate() {
        super.onCreate()
        database = Room.databaseBuilder(
            this,
            AppDatabase::class.java,
            "course.db"
        ).build()
        favoriteRepo = FavoriteRepository(database.favoriteDao())
    }
}