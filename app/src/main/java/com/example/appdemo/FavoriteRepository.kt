package com.example.appdemo

class FavoriteRepository(private val dao: FavoriteDao) {
    suspend fun toggleFavorite(courseId: Int) {
        if (dao.get(courseId) != null) {
            dao.delete(FavoriteCourse(courseId))
        } else {
            dao.insert(FavoriteCourse(courseId))
        }
    }

    suspend fun isFavorite(courseId: Int): Boolean {
        return dao.isFavorite(courseId) > 0
    }
}