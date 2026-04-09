package com.example.appdemo

import android.content.Context
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class CourseRepository(
    private val context: Context,
    private val favoriteRepo: FavoriteRepository
) {
    suspend fun getCourses(): List<Course> = withContext(Dispatchers.IO) {
        val json = context.assets.open("courses.json").bufferedReader().use { it.readText() }
        val gson = Gson()
        val wrapper = gson.fromJson(json, CourseWrapper::class.java)
        wrapper.courses.map { course ->
            course.copy(hasLike = favoriteRepo.isFavorite(course.id))
        }
    }

    data class CourseWrapper(val courses: List<Course>)
}