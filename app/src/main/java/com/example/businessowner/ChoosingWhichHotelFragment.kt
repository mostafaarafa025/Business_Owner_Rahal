package com.example.businessowner

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.businessowner.Ui.Insights.insights.InsightsActivity
import com.example.businessowner.Ui.Insights.viewmodel.RequestViewModel
import com.example.businessowner.adapters.ChooseHotelAdapter
import com.example.businessowner.databinding.FragmentChoosingWhichHotelBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ChoosingWhichHotelFragment : Fragment() {
    private val requestViewModel:RequestViewModel by viewModels()
    private lateinit var binding:FragmentChoosingWhichHotelBinding
    private var value:String=""
    private lateinit var chooseHotelAdapter: ChooseHotelAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
   chooseHotelAdapter= ChooseHotelAdapter(requestViewModel)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding= FragmentChoosingWhichHotelBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
         getChosenHotel()
         setUpHotelRecyclerView()
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
        binding.recyclerViewHotel.apply {
            adapter=chooseHotelAdapter
        }
    }
//    private fun setupRecyclerView(images: List<String>) {
//        val imageItems = images.map { ImageItem(it) }
//        val imageAdapter = ImageAdapter(imageItems)
//        val layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
//        binding.imageRecyclerView.layoutManager = layoutManager
//        binding.imageRecyclerView.adapter = imageAdapter
//    }

}