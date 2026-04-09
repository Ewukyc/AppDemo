package com.example.appdemo

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import java.text.SimpleDateFormat
import java.util.*

class CourseAdapter(
    private val onFavoriteClick: (Course) -> Unit
) : ListAdapter<Course, CourseAdapter.CourseViewHolder>(CourseDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CourseViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_course, parent, false)
        return CourseViewHolder(view, onFavoriteClick)
    }

    override fun onBindViewHolder(holder: CourseViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class CourseViewHolder(
        private val view: View,
        private val onFavoriteClick: (Course) -> Unit
    ) : RecyclerView.ViewHolder(view) {
        private val tvTitle = view.findViewById<TextView>(R.id.tvTitle)
        private val tvDescription = view.findViewById<TextView>(R.id.tvDescription)
        private val tvPrice = view.findViewById<TextView>(R.id.tvPrice)
        private val tvRate = view.findViewById<TextView>(R.id.tvRate)
        private val tvDate = view.findViewById<TextView>(R.id.tvDate)
        private val ivFavorite = view.findViewById<ImageView>(R.id.ivFavorite)
        private val courseImage = view.findViewById<ImageView>(R.id.courseImage)

        fun bind(course: Course) {
            tvTitle.text = course.title.trim()
            tvDescription.text = course.text.trim()
            tvPrice.text = course.price.trim() + " ₽"
            tvRate.text = course.rate.trim()

            // Форматируем дату публикации
            try {
                val inputFormat = SimpleDateFormat("yyyy-MM-dd", Locale("ru", "RU"))
                val outputFormat = SimpleDateFormat("dd MMMM yyyy", Locale("ru", "RU"))
                val date = inputFormat.parse(course.publishDate)
                tvDate.text = if (date != null) outputFormat.format(date) else course.publishDate
            } catch (e: Exception) {
                tvDate.text = course.publishDate
            }

            // Загружаем фотографию
            val imageResId = when {
                course.title.contains("java", ignoreCase = true) -> R.drawable.java
                course.title.contains("python", ignoreCase = true) -> R.drawable.python
                course.title.contains("3d", ignoreCase = true) -> R.drawable.a3d
                course.title.contains("аналит", ignoreCase = true) -> {
                    if (course.title.contains("системный", ignoreCase = true)) {
                        R.drawable.sa
                    } else {
                        R.drawable.anal
                    }
                }
                else -> R.drawable.java
            }
            courseImage.setImageResource(imageResId)

            // Иконка сердца
            ivFavorite.setImageResource(
                if (course.hasLike) R.drawable.ic_favorite_filled else R.drawable.ic_favorite_outline
            )
            ivFavorite.setOnClickListener {
                onFavoriteClick(course)
            }

            // КЛИК НА КАРТОЧКУ - ОТКРЫТЬ ДЕТАЛЬ КУРСА
            view.setOnClickListener {
                val context = view.context
                val intent = Intent(context, CourseDetailActivity::class.java).apply {
                    putExtra("courseId", course.id)
                    putExtra("courseTitle", course.title)
                    putExtra("coursePrice", course.price)
                    putExtra("courseRate", course.rate)
                    putExtra("courseText", course.text)
                    putExtra("courseStartDate", course.startDate)
                    putExtra("coursePublishDate", course.publishDate)
                    putExtra("hasLike", course.hasLike)
                }
                context.startActivity(intent)
            }
        }
    }
}

class CourseDiffCallback : DiffUtil.ItemCallback<Course>() {
    override fun areItemsTheSame(oldItem: Course, newItem: Course): Boolean =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: Course, newItem: Course): Boolean =
        oldItem == newItem
}