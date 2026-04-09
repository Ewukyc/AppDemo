package com.example.appdemo

import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class CourseDetailActivity : AppCompatActivity() {

    private var courseId: Int = 0
    private var isFavorite: Boolean = false
    private lateinit var favoriteRepo: FavoriteRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        try {
            setContentView(R.layout.activity_course_detail)

            val app = application as App
            favoriteRepo = app.favoriteRepo

            // Данные из Intent
            val courseTitle = intent.getStringExtra("courseTitle") ?: "Курс"
            val coursePrice = intent.getStringExtra("coursePrice") ?: "0"
            val courseRate = intent.getStringExtra("courseRate") ?: "0"
            val courseText = intent.getStringExtra("courseText") ?: ""
            val courseStartDate = intent.getStringExtra("courseStartDate") ?: ""
            val coursePublishDate = intent.getStringExtra("coursePublishDate") ?: ""
            courseId = intent.getIntExtra("courseId", 0)
            isFavorite = intent.getBooleanExtra("hasLike", false)

            // Иконка по названию
            val imageResId = when {
                courseTitle.contains("java", ignoreCase = true) -> R.drawable.java
                courseTitle.contains("python", ignoreCase = true) -> R.drawable.python
                courseTitle.contains("3d", ignoreCase = true) -> R.drawable.a3d
                courseTitle.contains("системный", ignoreCase = true) -> R.drawable.sa
                courseTitle.contains("аналит", ignoreCase = true) -> R.drawable.anal
                else -> R.drawable.java
            }

            // Устанавливаем UI элементы
            try {
                findViewById<ImageView>(R.id.courseDetailImage).setImageResource(imageResId)
            } catch (e: Exception) {}

            try {
                findViewById<TextView>(R.id.courseDetailTitle).text = courseTitle
            } catch (e: Exception) {}

            try {
                findViewById<TextView>(R.id.courseDetailDescription).text = courseText
            } catch (e: Exception) {}

            try {
                findViewById<TextView>(R.id.courseDetailPrice).text = "$coursePrice ₽"
            } catch (e: Exception) {}

            try {
                findViewById<TextView>(R.id.courseDetailRate).text = courseRate
            } catch (e: Exception) {}

            try {
                findViewById<TextView>(R.id.courseDetailStartDate).text = formatDate(courseStartDate)
            } catch (e: Exception) {}

            try {
                findViewById<TextView>(R.id.courseDetailPublishDate).text = formatDate(coursePublishDate)
            } catch (e: Exception) {}

            // Кнопка назад
            try {
                findViewById<ImageView>(R.id.btnBack).setOnClickListener {
                    finish()
                }
            } catch (e: Exception) {}

            // Иконка избранного
            try {
                val favoriteIcon = findViewById<ImageView>(R.id.courseDetailFavorite)
                updateFavoriteIcon(favoriteIcon)

                favoriteIcon.setOnClickListener {
                    lifecycleScope.launch {
                        favoriteRepo.toggleFavorite(courseId)
                        isFavorite = !isFavorite
                        updateFavoriteIcon(favoriteIcon)
                    }
                }
            } catch (e: Exception) {}

            // Кнопка "Начать курс"
            try {
                findViewById<Button>(R.id.btnStartCourse).setOnClickListener {
                    finish()
                }
            } catch (e: Exception) {}

        } catch (e: Exception) {
            e.printStackTrace()
            finish()
        }
    }

    private fun updateFavoriteIcon(icon: ImageView) {
        try {
            icon.setImageResource(
                if (isFavorite) R.drawable.ic_favorite_filled else R.drawable.ic_favorite_outline
            )
        } catch (e: Exception) {}
    }

    private fun formatDate(dateString: String): String {
        return try {
            val inputFormat = SimpleDateFormat("yyyy-MM-dd", Locale("ru", "RU"))
            val outputFormat = SimpleDateFormat("dd MMMM yyyy", Locale("ru", "RU"))
            val date = inputFormat.parse(dateString)
            if (date != null) outputFormat.format(date) else dateString
        } catch (e: Exception) {
            dateString
        }
    }
}