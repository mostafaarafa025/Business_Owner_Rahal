package com.example.businessowner.Ui.Insights.insights

import android.opengl.Visibility
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.example.businessowner.R
import com.example.businessowner.Ui.Insights.viewmodel.SharedViewModel
import com.example.businessowner.databinding.FragmentProfileBinding
import com.example.businessowner.model.Respond.Hotel.Document
import com.example.businessowner.model.Respond.Restaurant.DocumentRes
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment : Fragment() {

    lateinit var binding: FragmentProfileBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getRestaurantData()
        binding.closeNavigationDrawer.setOnClickListener {
            binding.drawerLayout.closeDrawer(binding.navigationView)

        }
        applyMethods()
    }

    fun onCreateOptionsMenu2(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.setting_business_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_too_bar, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_setting -> {
                binding.drawerLayout.openDrawer(binding.navigationView)
                return true
            }

            R.id.editProfileFragment -> {
                view?.let {
                    Navigation.findNavController(it)
                        .navigate(R.id.action_profileFragment2_to_editProfileFragment)
                }
                return true
            }

            else -> return super.onOptionsItemSelected(item)
        }
    }
    private fun getHotelData(){
        val sharedViewModel = ViewModelProvider(requireActivity()).get(SharedViewModel::class.java)
        sharedViewModel.hotelRequestLiveDataShared.observe(viewLifecycleOwner) { data ->
            val document: Document = data[0]
            binding.descriptionTv.text=document.Description
            binding.locationEt.text=document.address
            binding.placeEt.text=document.name
            binding.phoneEt.text=document.phone
            binding.hotelClassEt.text=document.rating.toString()

                Log.d("ReceivingFragment", "Received HotelResponse in Profile: $data")

        }
    }
    private fun getRestaurantData(){

        val sharedViewModel = ViewModelProvider(requireActivity()).get(SharedViewModel::class.java)
      sharedViewModel.restaurantRequestLiveDataShared.observe(viewLifecycleOwner){
              val documentRes:DocumentRes=it[0]
              resLayoutChange()
              binding.descriptionTv.text=documentRes.Description
              binding.locationEt.text=documentRes.address
              binding.placeEt.text=documentRes.name
              binding.phoneEt.text=documentRes.phone
                binding.cuisineEt.text= documentRes.cuisine.toString()
              binding.fromEt.text=documentRes.StartingTime.toString()
              binding.toEt.text=documentRes.closeAt.toString()
              binding.scheduleEt.text=documentRes.workingDays
          }
    }
    private fun resLayoutChange(){
        binding.restaurantAttributes.visibility=View.VISIBLE
        binding.hotelClassIv.visibility=View.GONE
        binding.hotelClassEt.visibility=View.GONE
    }

    private fun applyMethods(){
        val sharedViewModel = ViewModelProvider(requireActivity()).get(SharedViewModel::class.java)

        if (!sharedViewModel.hotelRequestLiveDataShared.value.isNullOrEmpty()) {
            getHotelData()
        }

        if (!sharedViewModel.restaurantRequestLiveDataShared.value.isNullOrEmpty()) {
            getRestaurantData()
        }
    }


}