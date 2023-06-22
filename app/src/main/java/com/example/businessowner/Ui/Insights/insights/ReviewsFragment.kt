package com.example.businessowner.Ui.Insights.insights

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels

import com.example.businessowner.Ui.Insights.viewmodel.RequestViewModel
import com.example.businessowner.adapters.ReviewsHotelAdapter
//import com.example.businessowner.adapters.ReviewsHotelAdapter
import com.example.businessowner.adapters.ReviewsRestaurantAdapter
import com.example.businessowner.databinding.FragmentReviewsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ReviewsFragment : Fragment() {
    lateinit var binding: FragmentReviewsBinding
    private val requestViewModel: RequestViewModel by viewModels()
    private lateinit var reviewsRestaurantAdapter: ReviewsRestaurantAdapter
    private lateinit var reviewsHotelAdapter: ReviewsHotelAdapter
    private lateinit var hotelId: String
    private lateinit var resId: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        reviewsRestaurantAdapter = ReviewsRestaurantAdapter(requestViewModel)
        reviewsHotelAdapter = ReviewsHotelAdapter(requestViewModel)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentReviewsBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        resId = activity?.intent?.getStringExtra("resId",).toString()
        hotelId = activity?.intent?.getStringExtra("hotelId").toString()
        Log.e("resId", resId)
        Log.e("hotelId", hotelId)

        setUpRestaurantReviews()
        setUpHotelsReviews()
        }

    private fun setUpHotelsReviews() {
        requestViewModel.getHotelReviews(hotelId)
        requestViewModel.getHotelReviewsLiveData.observe(viewLifecycleOwner){reviews->
            reviewsHotelAdapter.differ.submitList(reviews)
            Log.e("reviews",reviews.toString())
            binding.recyclerViewReviews.apply {
                adapter = reviewsHotelAdapter

            }
        }
    }

    private fun setUpRestaurantReviews() {
        requestViewModel.getRestaurantReviews(resId)
        requestViewModel.getRestaurantReviewsLiveData.observe(viewLifecycleOwner) {
            reviewsRestaurantAdapter.differ.submitList(it)
            binding.recyclerViewReviews.apply {
                adapter = reviewsRestaurantAdapter
            }
        }
    }
    }





