package com.example.mywatchlist.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.core.data.movie.model.Movie
import com.example.mywatchlist.databinding.ItemMovieBinding

class HomeAdapter(private val onItemClick: (com.example.core.data.movie.model.Movie) -> Unit) :
    ListAdapter<com.example.core.data.movie.model.Movie, HomeAdapter.MovieViewHolder>(MovieDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val binding = ItemMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieViewHolder(binding, onItemClick)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class MovieViewHolder(
        private val binding: ItemMovieBinding,
        private val onItemClick: (com.example.core.data.movie.model.Movie) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(movie: com.example.core.data.movie.model.Movie) {
            binding.titleMovie.text = movie.title
            binding.overviewMovie.text = movie.overview
            binding.ratingMovie.text = String.format("Rating: %.1f", movie.rating)
            Glide.with(binding.root)
                .load("https://image.tmdb.org/t/p/w500${movie.posterUrl}")
                .into(binding.posterMovie)

            binding.root.setOnClickListener {
                onItemClick(movie)
            }
        }
    }
}

class MovieDiffCallback : DiffUtil.ItemCallback<com.example.core.data.movie.model.Movie>() {
    override fun areItemsTheSame(oldItem: com.example.core.data.movie.model.Movie, newItem: com.example.core.data.movie.model.Movie): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: com.example.core.data.movie.model.Movie, newItem: com.example.core.data.movie.model.Movie): Boolean {
        return oldItem == newItem
    }
}