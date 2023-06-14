package com.example.businessowner.Ui.Insights.Signup

import android.content.ContentUris
import android.os.Bundle
import android.text.format.DateFormat.is24HourFormat
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.businessowner.LoadingOwnerFragment
import com.example.businessowner.R
import com.example.businessowner.databinding.FragmentSignUp2Binding
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import dagger.hilt.android.AndroidEntryPoint
import com.example.businessowner.Ui.Insights.viewmodel.PlaceViewModel
import com.example.businessowner.databinding.HotelLayoutBinding
import com.example.businessowner.databinding.RestaurantLayoutBinding
import com.example.businessowner.model.addingHotel.HotelRequest
import com.example.businessowner.model.addingHotel.LocationHotel
import com.example.businessowner.model.addingRestaurant.LocationRestaurant
import com.example.businessowner.model.addingRestaurant.RestaurantRequest
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SignUp2 : Fragment() {
    lateinit var binding: FragmentSignUp2Binding
    val ADD_PDF_REQUEST_CODE = 102
    private var cuisineList: List<String>? = emptyList()
    private var restaurantLayoutBinding: RestaurantLayoutBinding? = null
    private var hotelLayoutBinding: HotelLayoutBinding? = null
    private var selectedStartTimeString: String? = null
    private var selectedEndTimeString: String? = null
    private var imageUrls: List<String> = emptyList()
    private var coordinatesList: List<Double> = emptyList()
    private var government: String = ""
   private var placeName: String = ""
   private var hotelId: String = ""
   private var restaurantId: String = ""
    private var phoneNumber: String = ""
    private var workingWeekDays: String = ""
    private var description: String = ""
    private var fullAddress: String = ""
    private val viewModel: PlaceViewModel by viewModels()
    private var coordinates: DoubleArray = doubleArrayOf(0.0, 0.0)
    private var selectedEndTime: Double? = null
    private var classHotel: Double? = null
    private var selectedStartTime: Double? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSignUp2Binding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        categoryDropDown()
        getData()

        binding.backArrowButton.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.signUp1)
        }
        binding.submitButton.setOnClickListener {
            when (binding.categorySpinnerAutoComplete.text.toString()) {
                "Restaurant" -> addRestaurant()
                "Hotel" -> addHotel()
            }
        }

        binding.categorySpinnerAutoComplete.setOnItemClickListener { _, _, position, _ ->
            onCategorySelected(position)
        }

    }

    private fun daysDropDown() {
        val days = resources.getStringArray(R.array.days)
        val arrayAdapter = ArrayAdapter(requireContext(), R.layout.dropdown_item, days)
        restaurantLayoutBinding!!.daysSpinnerAutoComplete.setAdapter(arrayAdapter)
    }

    private fun categoryDropDown() {
        val category = resources.getStringArray(R.array.Category)
        val arrayAdapterCategory = ArrayAdapter(requireContext(), R.layout.dropdown_item, category)
        binding.categorySpinnerAutoComplete.setAdapter(arrayAdapterCategory)
    }

    private fun getData() {
        government = arguments?.getString("government") ?: ""
        fullAddress = arguments?.getString("fullAddress") ?: ""
        imageUrls = arguments?.getStringArrayList("imageUrls") ?: emptyList()
        coordinates = arguments?.getDoubleArray("coordinates") ?: doubleArrayOf(0.0, 0.0)
        Log.d("Address", fullAddress)
        Log.d("Government", government)
        Log.d("images", imageUrls.toString())
        coordinatesList = coordinates?.toList() ?: emptyList()
        Log.d("Coordinates List", coordinatesList.toString())
    }

    private fun openTimePickerFrom() {
        val isSystem24Hour = is24HourFormat(requireContext())
        val clockFormat = if (isSystem24Hour) TimeFormat.CLOCK_24H else TimeFormat.CLOCK_12H

        val picker = MaterialTimePicker.Builder()
            .setTimeFormat(clockFormat)
            .setHour(12)
            .setMinute(0)
            .setTitleText("from")
            .setInputMode(MaterialTimePicker.INPUT_MODE_KEYBOARD)  // Add this line to set the input mode to keyboard
            .build()
        picker.show(childFragmentManager, "TAG")
        picker.addOnPositiveButtonClickListener {
            val selectedHour = picker.hour
            val selectedMinute = picker.minute
            val totalMinutes = selectedHour * 60 + selectedMinute
            selectedStartTime = totalMinutes.toDouble() / 60.0
            selectedStartTimeString = String.format("%02d:%02d", selectedHour, selectedMinute)
            restaurantLayoutBinding?.editTextTimeFrom?.setText(selectedStartTimeString)
            Log.e("timeStart", selectedStartTime.toString())
        }
    }

    private fun openTimePickerTo() {
        val isSystem24Hour = is24HourFormat(requireContext())
        val clockFormat = if (isSystem24Hour) TimeFormat.CLOCK_24H else TimeFormat.CLOCK_12H

        val picker = MaterialTimePicker.Builder()
            .setTimeFormat(clockFormat)
            .setHour(12)
            .setMinute(0)
            .setTitleText("To")
            .setInputMode(MaterialTimePicker.INPUT_MODE_KEYBOARD)  // Add this line to set the input mode to keyboard
            .build()
        picker.show(childFragmentManager, "TAG")
        picker.addOnPositiveButtonClickListener {
            val selectedHour = picker.hour
            val selectedMinute = picker.minute
            val totalMinutes = selectedHour * 60 + selectedMinute
            selectedEndTime = totalMinutes.toDouble() / 60.0
            selectedEndTimeString = String.format("%02d:%02d", selectedHour, selectedMinute)

            // Update the selected time in the spinner
            restaurantLayoutBinding?.editTextTimeTo?.setText(selectedEndTimeString.toString())
            Log.e("timeEnd", selectedEndTime.toString())
            Log.e("timeEnd", selectedStartTimeString.toString())
        }
    }

    private fun onCategorySelected(position: Int) {
        when (position) {
            0 -> {
                showRestaurantLayout()
                daysDropDown()
                restaurantLayoutBinding!!.editTextTimeTo.setOnClickListener {
                    openTimePickerTo()
                }
                restaurantLayoutBinding!!.editTextTimeFrom.setOnClickListener {
                    openTimePickerFrom()
                }
            }

            1 -> showHotelLayout()
        }
    }

    private fun showRestaurantLayout() {
        if (restaurantLayoutBinding == null) {
            restaurantLayoutBinding = RestaurantLayoutBinding.inflate(layoutInflater)
        }
        binding.frameLayoutCategory.removeAllViews()
        binding.frameLayoutCategory.addView(restaurantLayoutBinding!!.root)
        val cuisine = restaurantLayoutBinding!!.cuisineListEditText.text.toString().trim()
        cuisineList = cuisine.split(",").map { it.trim() }

    }

    private fun showHotelLayout() {
        if (hotelLayoutBinding == null) {
            hotelLayoutBinding = HotelLayoutBinding.inflate(layoutInflater)
        }
        binding.frameLayoutCategory.removeAllViews()
        binding.frameLayoutCategory.addView(hotelLayoutBinding!!.root)
    }

    private fun addRestaurant() {
        placeName = binding.placeNameEditText.text.toString()
        phoneNumber = binding.phoneNumberEditText.text.toString()
        description = binding.descriptionEditText.text.toString()
        workingWeekDays = restaurantLayoutBinding?.daysSpinnerAutoComplete?.text.toString()
        val restaurantRequest = RestaurantRequest().apply {
            this.phone = phoneNumber
            this.name = placeName
            this.image = imageUrls
            this.workingDays = workingWeekDays
            this.closeAt = selectedEndTime.toString()
            this.StartingTime = selectedStartTime.toString()
            this.address = fullAddress
            this.city = government
            this.Description = description
            location = LocationRestaurant().apply {
                coordinates = coordinatesList
            }
        }
        viewModel.addRestaurant(restaurantRequest)

        viewModel.restaurantResponse.observe(viewLifecycleOwner) { restaurantResponse ->
            // Handle the restaurantResponse here
            Log.d("RestaurantFragment", "Response: $restaurantResponse")
            restaurantId=restaurantResponse.data.document.id
            Log.e("restaurantId",restaurantId)
            Toast.makeText(requireContext(), "Place Added", Toast.LENGTH_SHORT).show()
            var bundleResId=Bundle().apply {
                putString("RestaurantId",restaurantId)
            }
            val loadingFragment=LoadingOwnerFragment()
            loadingFragment.arguments=bundleResId
            CoroutineScope(Dispatchers.Main).launch {
                delay(2000)
                view?.findNavController()?.navigate(R.id.action_signUp2_to_loadingOwnerFragment,bundleResId)
            }

        }

        viewModel.error.observe(viewLifecycleOwner) { errorMessage ->
            errorMessage?.let {
                Toast.makeText(requireContext(), errorMessage, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun addHotel() {
        placeName = binding.placeNameEditText.text.toString()
        phoneNumber = binding.phoneNumberEditText.text.toString()
        description = binding.descriptionEditText.text.toString()
        hotelLayoutBinding!!.hotelRatingBar.setOnRatingBarChangeListener { _, rating, _ ->
            classHotel = rating.toDouble()
        }
        val hotelRequest = HotelRequest().apply {
            this.name = placeName
            this.phone = phoneNumber
            this.city = government
            this.address = fullAddress
            this.rating = classHotel
            this.Description = description
            this.image = imageUrls
            this.location = LocationHotel().apply {
                coordinates = coordinatesList
            }
        }
        viewModel.addHotel(hotelRequest)
        viewModel.hotelResponse.observe(viewLifecycleOwner) {
            Log.d("HotelFragment", "Response: $it")
          hotelId=  it.data.document.id
            Log.e("hotelId",hotelId)
            Toast.makeText(requireContext(), "Place Added", Toast.LENGTH_SHORT).show()
           var bundleHotelId=Bundle().apply {
               putString("HotelId",hotelId)
           }
            val loadingFragment=LoadingOwnerFragment()
            loadingFragment.arguments=bundleHotelId
            CoroutineScope(Dispatchers.Main).launch {
                delay(2000)
                view?.findNavController()
                    ?.navigate(R.id.action_signUp2_to_loadingOwnerFragment, bundleHotelId)
            }
        }
        viewModel.error.observe(viewLifecycleOwner) { errorMessage ->
            errorMessage?.let {
                Toast.makeText(requireContext(), errorMessage, Toast.LENGTH_SHORT).show()
            }
        }
    }
//    private fun sendingIds(){
////        var bundleId=Bundle().apply {
////            putString("HotelId",hotelId)
////            putString("RestaurantId",restaurantId)
////        }
////        val loadingFragment=LoadingOwnerFragment()
////        loadingFragment.arguments=bundleId
//       // view?.findNavController()?.navigate(R.id.action_signUp2_to_loadingOwnerFragment,bundleId)
//
//        val action=SignUp2Directions.actionSignUp2ToLoadingOwnerFragment(hotelId,restaurantId)
//        findNavController().navigate(action)
//    }
}