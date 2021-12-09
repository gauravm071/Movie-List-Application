package com.example.saveoassignment.screens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.saveoassignment.Constants.THUMBNAIL_BASE_URL
import com.example.saveoassignment.R
import com.example.saveoassignment.databinding.FragmentMovieDetailsBinding
import com.example.saveoassignment.model.Result
import com.google.gson.Gson

class MovieDetails : Fragment() {
    private lateinit var binding: FragmentMovieDetailsBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentMovieDetailsBinding.inflate(layoutInflater, container, false)
        (activity as AppCompatActivity?)!!.setSupportActionBar(binding.movieDetailScreenToolbar.myToolbar)
        setUpToolbar()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val movie = arguments?.getString("movie")
        val movieResult: Result = Gson().fromJson(movie, Result::class.java)
        setData(movieResult)
        binding.movieDetailScreenToolbar.leftIcon.setOnClickListener {
            findNavController().popBackStack(R.id.movieDetails,true)
        }
    }

    private fun setData(movieResult: Result) {
        binding.apply {
            Glide.with(requireContext())
                .load(THUMBNAIL_BASE_URL + movieResult.posterPath).centerCrop()
                .into(binding.movieThumbnailImageView)
            movieTitle.text = movieResult.originalTitle
            ratingsTextView.text = "Ratings: " + movieResult.voteAverage.toString()
            totalReviewsTextView.text = "Review Count: " + movieResult.voteCount.toString()
            languageTextView.text = "Language: " + movieResult.originalLanguage.uppercase()
            releaseDateTextView.text = "Release Date: " + movieResult.releaseDate
            movieDescription.text = movieResult.overview
        }
    }

    private fun setUpToolbar(){
        binding.movieDetailScreenToolbar.leftIcon.setImageDrawable(
            ContextCompat.getDrawable(
                requireContext(),
                R.drawable.ic_baseline_arrow_back_24
            )
        )

        binding.movieDetailScreenToolbar.rightIcon.setImageDrawable(
            ContextCompat.getDrawable(
                requireContext(),
                R.drawable.ic_baseline_share_24
            )
        )
    }
}
