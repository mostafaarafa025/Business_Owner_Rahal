package com.example.businessowner.Ui.Insights.insights

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels

import com.example.businessowner.databinding.FragmentInsightsBinding
import com.example.businessowner.model.Respond.Hotel.Document
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class InsightsFragment : Fragment() {
    lateinit var binding:FragmentInsightsBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding= FragmentInsightsBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



    }

    }



