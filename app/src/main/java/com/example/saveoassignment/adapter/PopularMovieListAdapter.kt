package com.example.saveoassignment.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.saveoassignment.databinding.PouplarMoviesItemBinding
import com.example.saveoassignment.model.Result

class PopularMovieListAdapter(private val listOfPopularMovies: List<Result>) :
    RecyclerView.Adapter<PopularMovieListAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            PouplarMoviesItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val mPosition = position % listOfPopularMovies.size
        holder.bind(listOfPopularMovies[mPosition])
    }

    override fun getItemCount(): Int {
        return listOfPopularMovies.size * 2
    }

    class ViewHolder(private val binding: PouplarMoviesItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(result: Result) {
            binding.apply {
                Glide.with(itemView.context)
                    .load("https://image.tmdb.org/t/p/w500/" + result.posterPath)
                    .into(binding.popularMovieThumbnail)
            }
        }
    }

    class CircularScrollListener(val items: Int) : RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)
            val firstItemVisible: Int =
                (recyclerView.layoutManager as LinearLayoutManager).findFirstVisibleItemPosition()
            if (firstItemVisible != 0 && firstItemVisible % items == 0) {
                recyclerView.layoutManager?.scrollToPosition(0)
            }
            val layoutManager = recyclerView.layoutManager
            val midpoint = layoutManager?.width?.div(2f)
        }
    }
}
