package com.example.mywatchlist.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.example.mywatchlist.R
import com.example.core.data.movie.model.Movie
import com.example.mywatchlist.databinding.FragmentDetailBinding
import com.example.mywatchlist.databinding.FragmentHomeBinding
import com.example.core.utils.Result
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DetailFragment : Fragment() {

    private val viewModel: DetailViewModel by viewModels()
    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Get movie ID and API key (you can get this from arguments or any other source)
        val movieId = arguments?.getInt("MOVIE_ID") ?: return
        val apiKey = "8c74f17a84f11134bec6ce0e6e72c8e8"

        setupFavoriteButton()

        // Call function to load movie details
        viewModel.getMovieDetail(movieId, apiKey)

        // Observe movie details LiveData
        viewModel.movieDetail.observe(viewLifecycleOwner) { result ->
            when (result) {
                is com.example.core.utils.Result.Loading -> {
                    // Show loading indicator (optional)
                }
                is com.example.core.utils.Result.Success -> {
                    val movie = result.data
                    binding.apply {
                        movieTitle.text = movie!!.title
                        releaseDate.text = getString(R.string.release_date, movie.releaseDate)
                        movieRating.text = getString(R.string.rating, movie.rating)
                        moviePopularity.text = getString(R.string.popularity, movie.popularity)
                        movieGenres.text = getString(R.string.genres, movie.genres?.joinToString(", "))
                        movieOverview.text = movie.overview

                        // Load image using Glide or Picasso
                        Glide.with(this@DetailFragment)
                            .load(movie.posterUrl)
                            .into(moviePoster)
                    }
                }
                is com.example.core.utils.Result.Error -> {
                    // Show error message
                    Toast.makeText(requireContext(), result.message, Toast.LENGTH_SHORT).show()
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.isFavorite.collect { isFavorite ->
                updateFavoriteButton(isFavorite)
            }
        }
    }

    private fun setupFavoriteButton() {
        binding.favoriteButton.setOnClickListener {
            viewModel.toggleFavorite()
            Toast.makeText(context, "Added to favorites", Toast.LENGTH_SHORT).show()
        }
    }

    private fun updateFavoriteButton(isFavorite: Boolean) {
        val icon = if (isFavorite) {
            R.drawable.baseline_favorite_24
        } else {
            R.drawable.baseline_favorite_border_24
        }
        binding.favoriteButton.setImageResource(icon)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}