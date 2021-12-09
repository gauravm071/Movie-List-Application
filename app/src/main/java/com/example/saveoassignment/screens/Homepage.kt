package com.example.saveoassignment.screens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.saveoassignment.CenterZoomLayoutManager
import com.example.saveoassignment.Constants.API_KEY
import com.example.saveoassignment.Constants.DEFAULT_LANGUAGE
import com.example.saveoassignment.R
import com.example.saveoassignment.adapter.PopularMovieListAdapter
import com.example.saveoassignment.adapter.RegularMovieListAdapter
import com.example.saveoassignment.databinding.FragmentHomepageBinding
import com.example.saveoassignment.model.MovieListResult
import com.example.saveoassignment.repository.MovieListRepository
import com.example.saveoassignment.retrofit.RetrofitInstance
import com.example.saveoassignment.services.MoviesRetriever
import com.example.saveoassignment.viewmodel.MovieListViewModelFactory
import com.example.saveoassignment.viewmodel.MoviesListViewModel
import com.google.gson.Gson

class Homepage : Fragment() {
    private lateinit var binding: FragmentHomepageBinding
    private lateinit var viewModel: MoviesListViewModel
    private lateinit var regularMovieListAdapter: RegularMovieListAdapter
    private lateinit var popularMovieListAdapter: PopularMovieListAdapter
    private var pageNo = 1
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentHomepageBinding.inflate(layoutInflater, container, false)
        (activity as AppCompatActivity?)!!.setSupportActionBar(binding.homepageToolbar.myToolbar)
        setUpToolbar()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.homepageToolbar.rightIcon.setOnClickListener {
            binding.searchView.isVisible = !binding.searchView.isVisible
        }
        val retrofitInstance = RetrofitInstance().getInstance()
        val moviesRetriever = MoviesRetriever(retrofitInstance)
        val repository = MovieListRepository(moviesRetriever)
        viewModel = ViewModelProvider(
            this,
            MovieListViewModelFactory(repository)
        ).get(MoviesListViewModel::class.java)

        fetchMovies()
        observeMoviesList()
        clickListeners()
    }

    private fun clickListeners() {
        binding.prevTextView.setOnClickListener {
            if (pageNo > 1) {
                pageNo -= 1
                if (pageNo == 1) {
                    binding.prevTextView.isVisible = false  // handeling visibility of prev button according to the situation
                }
                fetchMovies()
                observeMoviesList()
            }
        }

        binding.nextTextView.setOnClickListener {
            pageNo += 1
            fetchMovies()
            observeMoviesList()
            binding.prevTextView.isVisible = true
        }
    }

    private fun fetchMovies() {
        binding.loader.isVisible=true
        viewModel.getListOfMovies(API_KEY, DEFAULT_LANGUAGE, pageNo)
        viewModel.getListOfPopularMovies(API_KEY, DEFAULT_LANGUAGE, pageNo)
    }

    private fun observeMoviesList() {
        viewModel.regularMovieList.observe(viewLifecycleOwner, { MovieList ->
            binding.loader.isVisible=false
            setRegularListAdapter(MovieList)
        })

        viewModel.popularMovieList.observe(viewLifecycleOwner, { MovieList ->
            binding.loader.isVisible=false
            setPopularListAdapter(MovieList)
        })
    }

    private fun setPopularListAdapter(movies: MovieListResult?) {
        popularMovieListAdapter =
            movies?.results?.let { movieResult -> PopularMovieListAdapter(movieResult) }!!
        binding.popularMoviesRecyclerView.layoutManager =
            CenterZoomLayoutManager(requireContext(), LinearLayout.HORIZONTAL, false)

        binding.popularMoviesRecyclerView.adapter = popularMovieListAdapter
        binding.popularMoviesRecyclerView.addOnScrollListener(
            PopularMovieListAdapter.CircularScrollListener(
                movies.results.size
            )
        )
    }

    private fun setRegularListAdapter(movies: MovieListResult?) {
        regularMovieListAdapter = movies?.results?.let { movieResult ->
            RegularMovieListAdapter(movieResult) { position ->
                val gson = Gson()
                val json = gson.toJson(movieResult[position])
                val bundle = bundleOf("movie" to json)
                findNavController().navigate(R.id.action_homepage_to_movieDetails, bundle)
            }
        }!!
        binding.regularMovieListRecyclerView.layoutManager =
            GridLayoutManager(requireContext(), 3)
        binding.regularMovieListRecyclerView.adapter = regularMovieListAdapter
    }

    private fun setUpToolbar() {
        binding.homepageToolbar.leftIcon.setImageDrawable(
            ContextCompat.getDrawable(
                requireContext(),
                R.drawable.ic_setting
            )
        )
        binding.homepageToolbar.rightIcon.setImageDrawable(
            ContextCompat.getDrawable(
                requireContext(),
                R.drawable.ic_baseline_search_24
            )
        )
    }
}
