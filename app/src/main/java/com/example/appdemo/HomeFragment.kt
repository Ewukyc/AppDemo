package com.example.appdemo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.launch

class HomeFragment : Fragment() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: CourseAdapter
    private var courses: List<Course> = emptyList()
    private lateinit var favoriteRepo: FavoriteRepository

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        // Получаем репозиторий из Application
        val app = requireActivity().application as App
        favoriteRepo = app.favoriteRepo

        recyclerView = view.findViewById(R.id.rvCourses)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        adapter = CourseAdapter { course ->
            lifecycleScope.launch {
                favoriteRepo.toggleFavorite(course.id)
                // Обновляем список
                val updatedCourses: List<Course> = courses.map { c ->
                    if (c.id == course.id) c.copy(hasLike = !c.hasLike) else c
                }
                courses = updatedCourses
                adapter.submitList(updatedCourses)
            }
        }
        recyclerView.adapter = adapter

        // Загружаем курсы
        viewLifecycleOwner.lifecycleScope.launch {
            val repo = CourseRepository(requireContext(), favoriteRepo)
            courses = repo.getCourses()
            adapter.submitList(courses)
        }

        // Кнопка фильтра (сортировка по publishDate)
        view.findViewById<ImageButton>(R.id.btnFilter).setOnClickListener {
            val sorted = courses.sortedByDescending { it.publishDate }
            courses = sorted
            adapter.submitList(sorted)
        }

        return view
    }
}