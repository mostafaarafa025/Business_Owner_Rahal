package com.example.businessowner

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.businessowner.Ui.Insights.insights.InsightsFragment
import com.example.businessowner.Ui.Insights.viewmodel.RequestViewModel
import com.example.businessowner.Ui.Insights.viewmodel.SharedViewModel
import com.example.businessowner.databinding.FragmentLoadingOwnerBinding
import com.example.businessowner.model.Respond.Hotel.Document
import com.example.businessowner.model.Respond.Restaurant.DocumentRes
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LoadingOwnerFragment : Fragment() {
    private val requestViewModel: RequestViewModel by viewModels()
    private var hotelId: String = ""
    private var restaurantId: String = ""
    lateinit var binding: FragmentLoadingOwnerBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLoadingOwnerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getDataIds()
        CoroutineScope(Dispatchers.Main).launch {
            delay(2000)
         applyMethods()
        }
        binding.next.setOnClickListener {
            view.findNavController().navigate(R.id.action_loadingOwnerFragment_to_insightsFragment)
        }
    }

    private fun getDataIds() {
        restaurantId = arguments?.getString("resId") ?: ""
        hotelId = arguments?.getString("HotelId") ?: ""
        Log.e("hotelIdLoadingFragment", hotelId)
        Log.e("restaurantIdLoadingFragment", restaurantId)
    }

    private fun getHotelResponse() {
        requestViewModel.getHotelRequest(hotelId)
        requestViewModel.getHotelRequestLiveData.observe(viewLifecycleOwner) { data ->
            val document: Document = data[0]
            Log.d("LoadingFragment", "Hotel Response: $data")
            val sharedViewModel = ViewModelProvider(requireActivity()).get(SharedViewModel::class.java)
            sharedViewModel.setHotelRequest(data)
        }
    }

    private fun getRestaurantRequest() {
        requestViewModel.getRestaurantRequest(restaurantId)
        requestViewModel.getRestaurantLiveData.observe(viewLifecycleOwner) {it ->
            Log.d("LoadingFragment", "Restaurant Response: $it")
            val sharedViewModel=ViewModelProvider(requireActivity()).get(SharedViewModel::class.java)
            sharedViewModel.sendRestaurantRequest(it)
        }
    }

    private fun applyMethods() {
        if (restaurantId.isNotEmpty()) {
            getRestaurantRequest()
        }
        if (hotelId.isNotEmpty()) {
            getHotelResponse()
        }else
            Log.e("error","error")

    }
}



