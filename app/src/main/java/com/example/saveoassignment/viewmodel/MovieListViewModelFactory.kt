package com.example.saveoassignment.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.saveoassignment.repository.MovieListRepository

class MovieListViewModelFactory(private val movieListRepository: MovieListRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MoviesListViewModel(this.movieListRepository) as T
    }
}
