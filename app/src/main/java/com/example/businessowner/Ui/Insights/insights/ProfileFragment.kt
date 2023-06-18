package com.example.businessowner.Ui.Insights.insights

import android.app.AlertDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.businessowner.R
import com.example.businessowner.Ui.Insights.Signup.SignUpFragment
import com.example.businessowner.Ui.Insights.viewmodel.RequestViewModel
import com.example.businessowner.Ui.Insights.viewmodel.SharedViewModel
import com.example.businessowner.adapters.ImageAdapter
import com.example.businessowner.adapters.ImageItem
import com.example.businessowner.databinding.FragmentProfileBinding
import com.example.businessowner.model.Respond.Hotel.Document
import com.example.businessowner.model.Respond.Restaurant.DocumentRes
import com.example.businessowner.model.getRespond.restaurant.Restaurant
import dagger.hilt.android.AndroidEntryPoint
import java.util.regex.Pattern

@AndroidEntryPoint
class ProfileFragment : Fragment() {
    private val requestViewModel: RequestViewModel by viewModels()
    private var coordinatesList: List<Double> = emptyList()
    lateinit var binding: FragmentProfileBinding
    private var countIndex:Int = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding= FragmentProfileBinding.inflate(inflater,container,false)
       setHasOptionsMenu(true)
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        countIndex = activity?.intent?.getIntExtra("countIndex",0)!!
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
      //  getRestaurantData()
        binding.closeNavigationDrawer.setOnClickListener {
            binding.drawerLayout.closeDrawer(binding.navigationView)

        }
        Log.e("count",countIndex.toString())
        getRestaurantResponse()
       // applyMethods()
        binding.mapIv.setOnClickListener {
            openMaps()
        }
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
            coordinatesList=document.location.coordinates
            binding.phoneEt.text=document.phone
            binding.hotelClassEt.text=document.rating.toString()
            setupRecyclerView(document.image)
                Log.d("ReceivingFragment", "Received HotelResponse in Profile: $data")
                Log.e("images",document.image.toString())
        }
    }

    private fun resLayoutChange(){
        binding.restaurantAttributes.visibility=View.VISIBLE
        binding.hotelClassIv.visibility=View.GONE
        binding.hotelClassEt.visibility=View.GONE
    }
//
//    private fun applyMethods(){
//        val sharedViewModel = ViewModelProvider(requireActivity()).get(SharedViewModel::class.java)
//
//        if (!sharedViewModel.hotelRequestLiveDataShared.value.isNullOrEmpty()) {
//            getHotelData()
//        }
//
//        if (!sharedViewModel.restaurantRequestLiveDataShared.value.isNullOrEmpty()) {
//            getRestaurantData()
//        }
//    }

    private fun setupRecyclerView(images: List<String>) {
        val imageItems = images.map { ImageItem(it) }
        val imageAdapter = ImageAdapter(imageItems)
        val layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.imageRecyclerView.layoutManager = layoutManager
        binding.imageRecyclerView.adapter = imageAdapter
    }
        private fun openMaps(){
        val pattern = Pattern.compile("\\[(.*),(.*)\\]")
        val matcher = pattern.matcher(coordinatesList.toString())
        if (matcher.find()) {
            val latitude = matcher.group(1)
            val longitude = matcher.group(2)

            val intentUri = "geo:$latitude,$longitude"
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(intentUri))
            startActivity(intent)
         }
      }
    fun showDialog() {
        val builder = AlertDialog.Builder(activity)
        builder.setMessage("Are u sure want Logout?")
            .setTitle("")
            .setPositiveButton("Yes") { dialog, id ->

                startActivity(Intent(activity, SignUpFragment::class.java))
                activity?.finish()
            }
            .setNegativeButton("Cancel") { dialog, id ->
                // User clicked No button
            }

        val dialog = builder.create()
        dialog.show()
    }

    private fun menus(){
        binding.navigationView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.action_setting -> {
                    // Handle Settings menu item click
                    // Perform the desired action here
                    binding.drawerLayout.openDrawer(binding.navigationView)
                    true
                }
                R.id.editProfileFragment -> {
                    // Handle Edit Profile menu item click
                    // Perform the desired action here
                    true
                }
                R.id.changePassword -> {
                    // Handle Change Password menu item click
                    // Perform the desired action here
                    true
                }
                R.id.logout -> {
                    // Handle Logout menu item click
                    // Perform the desired action here
                    showDialog()
                    true
                }
                else -> false
            }
        }

//        binding.yourButtonId.setOnClickListener {
//            binding.navigationView.openDrawer(GravityCompat.END) // Open the menu drawer
//        }
    }

    override fun onDestroy() {
        super.onDestroy()
    }

        private fun getRestaurantResponse(){
            requestViewModel.getRestaurantResponse()
            requestViewModel.getRestaurantResponseLiveData.observe(viewLifecycleOwner){ it->
                val restaurant : Restaurant =it[countIndex]
                Log.d("restaurant", "Restaurant Response: $it")
                resLayoutChange()
                setupRecyclerView(restaurant.image)
                binding.descriptionTv.text=restaurant.Description
                binding.locationEt.text=restaurant.address
                binding.placeEt.text=restaurant.name
                coordinatesList=restaurant.location.coordinates
                binding.phoneEt.text=restaurant.phone
                binding.cuisineEt.text= restaurant.cuisine.toString()
                binding.fromEt.text=restaurant.StartingTime.toString()
                binding.toEt.text=restaurant.closeAt.toString()
                binding.scheduleEt.text=restaurant.workingDays
            }
        }
    }
