package com.example.businessowner

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.businessowner.Ui.Insights.insights.InsightsActivity
import com.example.businessowner.Ui.Insights.viewmodel.RequestViewModel
import com.example.businessowner.adapters.ChooseHotelAdapter
import com.example.businessowner.adapters.ChooseRestaurantAdapter
import com.example.businessowner.databinding.FragmentChoosingWhichPlaceBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ChoosingWhichPlaceFragment : Fragment() {
    private val requestViewModel:RequestViewModel by viewModels()
    lateinit var binding:FragmentChoosingWhichPlaceBinding
    private lateinit var chooseHotelAdapter: ChooseHotelAdapter
    private lateinit var chooseRestaurantAdapter: ChooseRestaurantAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        chooseRestaurantAdapter= ChooseRestaurantAdapter(requestViewModel)
        chooseHotelAdapter=ChooseHotelAdapter(requestViewModel)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding= FragmentChoosingWhichPlaceBinding.inflate(inflater,container,false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
       getChosenRestaurant()
        setUpRestaurantRecyclerView()
        getChosenHotel()
        setUpHotelRecyclerView()

    }
    private fun getChosenRestaurant(){
        requestViewModel.getRestaurantResponse()
        requestViewModel.getRestaurantResponseLiveData.observe(viewLifecycleOwner){ it->
            chooseRestaurantAdapter.differ.submitList(it)
        }
        chooseRestaurantAdapter.onItemClickListener={ restaurant, position ->
            val countIndexRes=position.toString()
            val resId=restaurant.id
            Log.d("ItemClicked", "Position: $position")
            val intent= Intent(requireContext(), InsightsActivity::class.java)
            intent.putExtra("countIndexRes",countIndexRes )
            intent.putExtra("resId",resId)
            startActivity(intent)
        }
    }
    private fun setUpRestaurantRecyclerView(){
        binding.recyclerViewPlace.apply {
            adapter=chooseRestaurantAdapter
        }
    }
        private fun getChosenHotel(){
            requestViewModel.getHotelResponse()
            requestViewModel.getHotelResponseLiveData.observe(viewLifecycleOwner){
                chooseHotelAdapter.differ.submitList(it)
            }
            chooseHotelAdapter.onItemClickListener={hotel,position ->
                val countIndexHotel=position.toString()
                val hotelId=hotel.id
                Log.d("HotelClicked","Position: $position")
                val intent= Intent(requireContext(), InsightsActivity::class.java)
                intent.putExtra("countIndexHotel",countIndexHotel)
                intent.putExtra("hotelId",hotelId)
                startActivity(intent)
            }
        }
    private fun setUpHotelRecyclerView(){
        binding.recyclerViewPlace.apply {
            adapter=chooseHotelAdapter
        }
    }

}