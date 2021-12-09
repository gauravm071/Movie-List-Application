package com.example.saveoassignment.repository

import android.util.Log
import com.example.saveoassignment.model.MovieListResult
import com.example.saveoassignment.services.MoviesRetriever
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.Response

class MovieListRepository(private val moviesRetriever: MoviesRetriever) {

    suspend fun getListOfMovies(
        key: String,
        language: String,
        pageNo: Int
    ): Flow<Response<MovieListResult>> =
        flow {
            val response = moviesRetriever.getListOfMovies(key, language, pageNo)
            if (response?.isSuccessful == true) {
                emit(response)
            } else {
                Log.v("ERROR_ON_REPO: ", response?.body().toString())
            }
        }

    suspend fun getListOfPopularMovies(
        key: String,
        language: String,
        pageNo: Int
    ): Flow<Response<MovieListResult>> =
        flow {
            val response = moviesRetriever.getListOfPopularMovies(key, language, pageNo)
            if (response?.isSuccessful == true) {
                emit(response)
            } else {
                Log.v("ERROR_ON_REPO: ", response?.body().toString())
            }
        }
}
