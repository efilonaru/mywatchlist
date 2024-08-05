package com.example.mywatchlist.home

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.load.engine.Resource
import com.example.mywatchlist.R
import com.example.mywatchlist.core.ui.ViewModelFactory
import com.example.mywatchlist.databinding.FragmentHomeBinding
import com.example.mywatchlist.utils.Result
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class Home : Fragment() {

    private val viewModel: MovieViewModel by viewModels()
    private lateinit var binding: FragmentHomeBinding
    private lateinit var movieAdapter: HomeAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        observeViewModel()
        viewModel.getPopularMovies("8c74f17a84f11134bec6ce0e6e72c8e8")
    }

    private fun setupRecyclerView() {
        movieAdapter = HomeAdapter { movie ->
            // Handle item click (e.g., navigate to detail)
        }
        binding.rvMovies.apply {
            adapter = movieAdapter
            layoutManager = LinearLayoutManager(context)
        }
    }

    private fun observeViewModel() {
        viewModel.movies.observe(viewLifecycleOwner) { resource ->
            when (resource) {
                is Result.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                    binding.rvMovies.visibility = View.GONE
                }
                is Result.Success -> {
                    binding.progressBar.visibility = View.GONE
                    binding.rvMovies.visibility = View.VISIBLE
                    movieAdapter.submitList(resource.data)
                }
                is Result.Error -> {
                    binding.progressBar.visibility = View.GONE
                    Toast.makeText(context, resource.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}