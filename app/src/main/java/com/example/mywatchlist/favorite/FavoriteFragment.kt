package com.example.mywatchlist.favorite

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.core.data.movie.model.Movie
import com.example.mywatchlist.R
import com.example.mywatchlist.databinding.FragmentFavoriteBinding
import com.example.mywatchlist.home.HomeAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FavoriteFragment : Fragment() {

    private val viewModel: FavoriteViewModel by viewModels()
    private lateinit var binding: FragmentFavoriteBinding
    private lateinit var favoriteAdapter: HomeAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentFavoriteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        observeViewModel()
    }

    private fun setupRecyclerView() {
        favoriteAdapter = HomeAdapter { movie ->
            val bundle = Bundle().apply {
                putInt("MOVIE_ID", movie.id)
            }
            findNavController().navigate(R.id.action_fragment_favorite_to_fragment_details, bundle)
        }
        binding.rvFavorite.apply {
            adapter = favoriteAdapter
            layoutManager = LinearLayoutManager(context)
        }
    }

    private fun observeViewModel() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.favoriteMovies.collectLatest { result ->
                    when (result) {
                        is com.example.core.utils.Result.Loading<*> -> {
                            binding.progressBar.visibility = View.VISIBLE
                            binding.rvFavorite.visibility = View.GONE
                        }
                        is com.example.core.utils.Result.Success -> {
                            binding.progressBar.visibility = View.GONE
                            binding.rvFavorite.visibility = View.VISIBLE
                            favoriteAdapter.submitList(result.data as MutableList<Movie>?)
                        }
                        is com.example.core.utils.Result.Error<*> -> {
                            binding.progressBar.visibility = View.GONE
                            Toast.makeText(context, result.message, Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.getFavoriteMovies() // Refresh the favorite movies data
    }
}