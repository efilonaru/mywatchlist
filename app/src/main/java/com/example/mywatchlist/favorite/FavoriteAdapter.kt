package com.example.mywatchlist.favorite

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mywatchlist.databinding.ItemMovieBinding

class FavoriteAdapter : ListAdapter<com.example.core.data.movie.model.Movie, FavoriteAdapter.FavoriteViewHolder>(MovieDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
        val binding = ItemMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FavoriteViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class FavoriteViewHolder(
        private val binding: ItemMovieBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(movie: com.example.core.data.movie.model.Movie) {
            binding.titleMovie.text = movie.title
            binding.overviewMovie.text = movie.overview
            binding.ratingMovie.text = String.format("Rating: %.1f", movie.rating)
            Glide.with(binding.root)
                .load("https://image.tmdb.org/t/p/w500${movie.posterUrl}")
                .into(binding.posterMovie)
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
}