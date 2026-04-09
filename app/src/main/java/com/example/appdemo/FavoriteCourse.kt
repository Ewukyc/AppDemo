package com.example.appdemo

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorites")
data class FavoriteCourse(
    @PrimaryKey
    val courseId: Int
)