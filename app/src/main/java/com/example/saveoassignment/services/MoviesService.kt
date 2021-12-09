package com.example.saveoassignment.services

import com.example.saveoassignment.model.MovieListResult
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MoviesService {
    @GET("movie/top_rated")
    suspend fun getListOfRegularMovies(
        @Query("api_key") key: String,
        @Query("language") language: String,
        @Query("page") page: Int,
    ): Response<MovieListResult>

    @GET("movie/popular")
    suspend fun getPopularMovies(
        @Query("api_key") key: String,
        @Query("language") language: String,
        @Query("page") page: Int,
    ): Response<MovieListResult>
}
