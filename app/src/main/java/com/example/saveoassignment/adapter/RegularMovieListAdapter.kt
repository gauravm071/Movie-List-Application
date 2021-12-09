package com.example.saveoassignment.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.saveoassignment.databinding.RegularMovieItemBinding
import com.example.saveoassignment.model.Result

class RegularMovieListAdapter(
    private val listOfRegularMovies: List<Result>,
    private val listener: (Int) -> Unit
) : RecyclerView.Adapter<RegularMovieListAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            RegularMovieItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(listOfRegularMovies[position], listener)
    }

    override fun getItemCount(): Int {
        return listOfRegularMovies.size
    }

    class ViewHolder(private val binding: RegularMovieItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(result: Result, listener: (Int) -> Unit) {
            binding.apply {
                Glide.with(itemView.context)
                    .load("https://image.tmdb.org/t/p/w500/" + result.posterPath).centerCrop()
                    .into(binding.regularMovieThumbnail)
            }
            itemView.setOnClickListener {
                listener(adapterPosition)
            }
        }
    }

}
