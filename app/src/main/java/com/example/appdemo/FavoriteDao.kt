package com.example.appdemo

import androidx.room.*

@Dao
interface FavoriteDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(favorite: FavoriteCourse)

    @Delete
    suspend fun delete(favorite: FavoriteCourse)

    @Query("SELECT * FROM favorites WHERE courseId = :courseId")
    suspend fun get(courseId: Int): FavoriteCourse?

    @Query("SELECT COUNT(*) FROM favorites WHERE courseId = :courseId")
    suspend fun isFavorite(courseId: Int): Int
}