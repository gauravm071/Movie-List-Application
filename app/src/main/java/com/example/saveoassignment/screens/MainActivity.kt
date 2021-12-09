package com.example.saveoassignment.screens

import android.os.Bundle
import android.util.Log
import android.widget.LinearLayout.HORIZONTAL
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import androidx.recyclerview.widget.GridLayoutManager
import com.example.saveoassignment.CenterZoomLayoutManager
import com.example.saveoassignment.Constants.API_KEY
import com.example.saveoassignment.R
import com.example.saveoassignment.adapter.PopularMovieListAdapter
import com.example.saveoassignment.adapter.RegularMovieListAdapter
import com.example.saveoassignment.databinding.ActivityMainBinding
import com.example.saveoassignment.model.MovieListResult
import com.example.saveoassignment.repository.MovieListRepository
import com.example.saveoassignment.retrofit.RetrofitInstance
import com.example.saveoassignment.services.MoviesRetriever
import com.example.saveoassignment.viewmodel.MovieListViewModelFactory
import com.example.saveoassignment.viewmodel.MoviesListViewModel

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}
