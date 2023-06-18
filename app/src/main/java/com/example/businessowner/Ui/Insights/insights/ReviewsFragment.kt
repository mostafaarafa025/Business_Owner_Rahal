package com.example.businessowner.Ui.Insights.insights

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels

import com.example.businessowner.Ui.Insights.viewmodel.RequestViewModel
import com.example.businessowner.adapters.ReviewsAdapter
import com.example.businessowner.databinding.FragmentReviewsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ReviewsFragment : Fragment() {
    lateinit var binding:FragmentReviewsBinding
    private val requestViewModel: RequestViewModel by viewModels()
    private lateinit var reviewsAdapter: ReviewsAdapter

    private  lateinit var resId:String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        resId = activity?.intent?.getStringExtra("resId",).toString()
       reviewsAdapter=ReviewsAdapter(requestViewModel)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
       binding= FragmentReviewsBinding.inflate(inflater,container,false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.e("resId",resId)
        requestViewModel.getRestaurantReviews(resId)
        requestViewModel.getRestaurantReviewsLiveData.observe(viewLifecycleOwner){
        reviewsAdapter.differ.submitList(it)
        binding.recyclerViewReviews.apply {
            adapter=reviewsAdapter
        }
        }
    }



}