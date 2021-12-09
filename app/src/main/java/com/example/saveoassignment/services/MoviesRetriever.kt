package com.example.saveoassignment.services

import com.example.saveoassignment.model.MovieListResult
import retrofit2.Response
import retrofit2.Retrofit

class MoviesRetriever(private val retrofit: Retrofit) {
    private var moviesService: MoviesService? = null
    private fun getService(): MoviesService? {
        if (moviesService == null) {
            moviesService = retrofit.create(MoviesService::class.java)
        }
        return moviesService
    }

    suspend fun getListOfMovies(
        key: String,
        language: String,
        pageNO: Int
    ): Response<MovieListResult>? {
        return getService()?.getListOfRegularMovies(key, language, pageNO)
    }

    suspend fun getListOfPopularMovies(
        key: String,
        language: String,
        pageNO: Int
    ): Response<MovieListResult>? {
        return getService()?.getPopularMovies(key, language, pageNO)
    }
}
