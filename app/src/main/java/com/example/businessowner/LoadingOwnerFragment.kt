package com.example.businessowner

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.businessowner.Ui.Insights.insights.InsightsActivity
import com.example.businessowner.Ui.Insights.viewmodel.PlaceViewModel
import com.example.businessowner.Ui.Insights.viewmodel.RequestViewModel
import com.example.businessowner.databinding.FragmentLoadingOwnerBinding
import com.example.businessowner.model.Respond.Hotel.Document
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LoadingOwnerFragment : Fragment() {
    private val requestViewModel:RequestViewModel by viewModels()
    private var hotelId: String = ""
    private var restaurantId: String = ""
    lateinit var binding:FragmentLoadingOwnerBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
       binding= FragmentLoadingOwnerBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getDataIds()
       applyMethods()
        binding.next.setOnClickListener {
            val intent=Intent(requireContext(), InsightsActivity::class.java)
            startActivity(intent)
        }
    }
     private fun getDataIds(){
             hotelId= arguments?.getString("HotelId") ?: ""
         restaurantId=arguments?.getString("RestaurantId") ?: ""
         Log.e("hotelIdLoadingFragment",hotelId)
         Log.e("restaurantIdLoadingFragment",restaurantId)
     }
    private fun getHotelResponse(){
        requestViewModel.getHotelRequest(hotelId)
            requestViewModel.getHotelRequestLiveData.observe(viewLifecycleOwner){
                Log.d("LoadingFragment", "Hotel Response: $it")
            }
    }
    private fun getRestaurantRequest(){
        requestViewModel.getRestaurantRequest(restaurantId)
        requestViewModel.getRestaurantLiveData.observe(viewLifecycleOwner){
            Log.d("LoadingFragment", "Restaurant Response: $it")
        }
    }

   private fun applyMethods(){
        if (hotelId.isNotEmpty()){
            getHotelResponse()
        }
         if (restaurantId.isNotEmpty()){
            getRestaurantRequest()
        }else
            Log.e("error","error")
    }



}