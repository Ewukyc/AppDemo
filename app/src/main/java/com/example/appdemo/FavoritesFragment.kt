package com.example.appdemo

import com.example.appdemo.FavoriteRepository
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.launch

class FavoritesFragment : Fragment() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: CourseAdapter
    private lateinit var favoriteRepo: FavoriteRepository

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_favorites, container, false)

        val app = requireActivity().application as App
        favoriteRepo = app.favoriteRepo

        recyclerView = view.findViewById(R.id.rvFavorites)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        adapter = CourseAdapter { course ->
            lifecycleScope.launch {
                favoriteRepo.toggleFavorite(course.id)
                loadFavorites()
            }
        }
        recyclerView.adapter = adapter

        loadFavorites()

        return view
    }

    private fun loadFavorites() {
        viewLifecycleOwner.lifecycleScope.launch {
            val repo = CourseRepository(context = requireContext(), favoriteRepo = favoriteRepo)
            val allCourses = repo.getCourses()
            val favorites = allCourses.filter { it.hasLike }
            adapter.submitList(favorites)
        }
    }
}