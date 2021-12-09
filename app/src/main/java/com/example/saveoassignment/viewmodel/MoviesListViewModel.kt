package com.example.saveoassignment.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.saveoassignment.model.MovieListResult
import com.example.saveoassignment.repository.MovieListRepository
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class MoviesListViewModel(private val movieListRepository: MovieListRepository) : ViewModel() {
    private val _regularMovieList = MutableLiveData<MovieListResult>()
    val regularMovieList: LiveData<MovieListResult>
        get() = _regularMovieList

    private val _popularMovieList = MutableLiveData<MovieListResult>()
    val popularMovieList: LiveData<MovieListResult>
        get() = _popularMovieList

    fun getListOfMovies(key: String, language: String, pageNo: Int) {
        viewModelScope.launch {
            try {
                val result = movieListRepository.getListOfMovies(key, language, pageNo)
                result.collect { movieListResult ->
                    _regularMovieList.postValue(movieListResult.body())
                }
            } catch (e: Exception) {
                Log.v("ERROR: ", e.message.toString())
            }
        }
    }

    fun getListOfPopularMovies(key: String, language: String, pageNo: Int) {
        viewModelScope.launch {
            try {
                val result = movieListRepository.getListOfPopularMovies(key, language, pageNo)
                result.collect { popularMovieListResult ->
                    _popularMovieList.postValue(popularMovieListResult.body())
                }
            } catch (e: Exception) {
                Log.v("ERROR: ", e.message.toString())
            }
        }
    }
}
