package com.example.businessowner.Ui.Insights.Signup

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.format.DateFormat.is24HourFormat
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import com.example.businessowner.R
import com.example.businessowner.databinding.FragmentSignUp2Binding
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import dagger.hilt.android.AndroidEntryPoint
import com.example.businessowner.Ui.Insights.viewmodel.PlaceViewModel
import com.example.businessowner.databinding.HotelLayoutBinding
import com.example.businessowner.databinding.RestaurantLayoutBinding
import com.example.businessowner.model.addingRestaurant.LocationRestaurant
import com.example.businessowner.model.addingRestaurant.RestaurantRequest

@AndroidEntryPoint
class SignUp2 : Fragment() {
   lateinit var binding: FragmentSignUp2Binding
    val ADD_PDF_REQUEST_CODE = 102
    private var cuisineList: List<String>? = emptyList()
    private var restaurantLayoutBinding: RestaurantLayoutBinding? = null
    private var hotelLayoutBinding: HotelLayoutBinding? = null
    private var selectedStartTime: String? = null
    private var coordinatesList: List<Double> = emptyList()
   private var government: String = ""
    private var fullAddress: String = ""
    private val viewModel:PlaceViewModel by viewModels()
    private var coordinates: DoubleArray = doubleArrayOf(0.0, 0.0)
    private var selectedEndTime: String? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentSignUp2Binding.inflate(inflater,container,false)
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
        addRestaurant()
          //Navigation.findNavController(view).navigate(R.id.loadingOwnerFragment)
      }
        binding.iconUpload.setOnClickListener {
            pickPdf()
            putPdf()
        }
        binding.categorySpinnerAutoComplete.setOnItemClickListener { _, _, position, _ ->
            onCategorySelected(position)
        }

    }
    private fun daysDropDown(){
    val days = resources.getStringArray(R.array.days)
    val arrayAdapter= ArrayAdapter(requireContext(), R.layout.dropdown_item,days)
  restaurantLayoutBinding!!.daysSpinnerAutoComplete.setAdapter(arrayAdapter)
}
    private fun categoryDropDown(){
        val category=resources.getStringArray(R.array.Category)
        val arrayAdapterCategory=ArrayAdapter(requireContext(), R.layout.dropdown_item,category)
        binding.categorySpinnerAutoComplete.setAdapter(arrayAdapterCategory)
    }

    private fun getData(){
        government = arguments?.getString("government") ?: ""
        fullAddress = arguments?.getString("fullAddress") ?: ""
        coordinates = arguments?.getDoubleArray("coordinates") ?: doubleArrayOf(0.0, 0.0)
        Log.d("Address", fullAddress)
        Log.d("Government", government)
       coordinatesList = coordinates?.toList() ?: emptyList()
        Log.d("Coordinates List", coordinatesList.toString())
    }
    private fun openTimePickerFrom() {
      val isSystem24Hour = is24HourFormat(requireContext())
       val clockFormat = if (isSystem24Hour) TimeFormat.CLOCK_24H else TimeFormat.CLOCK_12H

        val picker=MaterialTimePicker.Builder()
            .setTimeFormat(clockFormat)
            .setHour(12)
            .setMinute(0)
            .setTitleText("from")
            .setInputMode(MaterialTimePicker.INPUT_MODE_KEYBOARD)  // Add this line to set the input mode to keyboard
            .build()
      picker.show(childFragmentManager,"TAG")
        picker.addOnPositiveButtonClickListener {
            val selectedHour = picker.hour
            val selectedMinute = picker.minute
            selectedStartTime = String.format("%02d:%02d", selectedHour, selectedMinute)
            restaurantLayoutBinding?.editTextTimeFrom?.setText(selectedStartTime)
            Log.e("timeStart",selectedStartTime!!)
        }
    }
    private fun openTimePickerTo() {
        val isSystem24Hour = is24HourFormat(requireContext())
        val clockFormat = if (isSystem24Hour) TimeFormat.CLOCK_24H else TimeFormat.CLOCK_12H

        val picker=MaterialTimePicker.Builder()
            .setTimeFormat(clockFormat)
            .setHour(12)
            .setMinute(0)
            .setTitleText("To")
            .setInputMode(MaterialTimePicker.INPUT_MODE_KEYBOARD)  // Add this line to set the input mode to keyboard
            .build()
        picker.show(childFragmentManager,"TAG")
        picker.addOnPositiveButtonClickListener {
            val selectedHour = picker.hour
            val selectedMinute = picker.minute
           selectedEndTime = String.format("%02d:%02d", selectedHour, selectedMinute)

            // Update the selected time in the spinner
           restaurantLayoutBinding?.editTextTimeTo?.setText(selectedEndTime)
            Log.e("timeEnd", selectedEndTime!!)
        }
    }
        private fun onCategorySelected(position:Int){
            when(position){
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

        private fun showRestaurantLayout(){
            if (restaurantLayoutBinding == null) {
                restaurantLayoutBinding = RestaurantLayoutBinding.inflate(layoutInflater)
            }
            binding.frameLayoutCategory.removeAllViews()
            binding.frameLayoutCategory.addView(restaurantLayoutBinding!!.root)
            val cuisine=restaurantLayoutBinding!!.cuisineListEditText.text.toString().trim()
             cuisineList = cuisine.split(",").map { it.trim() }


        }
        private fun showHotelLayout(){
            if (hotelLayoutBinding == null) {
                hotelLayoutBinding = HotelLayoutBinding.inflate(layoutInflater)
            }
            binding.frameLayoutCategory.removeAllViews()
            binding.frameLayoutCategory.addView(hotelLayoutBinding!!.root)
        }
         private fun pickPdf() {
        val pdfIntent = Intent(Intent.ACTION_GET_CONTENT)
        pdfIntent.type = "application/pdf"
        startActivityForResult(pdfIntent, ADD_PDF_REQUEST_CODE)
    }
        override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == ADD_PDF_REQUEST_CODE && resultCode == Activity.RESULT_OK && data != null) {
            // Handle photo selection as before
            val selectedPdfUri = data.data
      }
    }
         private fun putPdf() {
        binding.pdfWebView.loadUrl("https://docs.google.com/gview?embedded=true&url=selectedPdfUri")
        binding.pdfWebView.visibility=View.VISIBLE
        // Do whatever you need to do with the selected PDF file (e.g., upload to a server)
        binding.button.visibility=View.GONE
        binding.iconUpload.visibility=View.GONE
    }

    private fun addRestaurant(){
        val name=binding.placeNameEditText.text.toString()
        val phoneNumber=binding.phoneNumberEditText.text.toString()
        val restaurantRequest=RestaurantRequest().apply {
            this.phone=phoneNumber
            this.name=name
            this.rating=2.6
            this.address=fullAddress
            this.city=government
            this.cuisine=cuisineList

            location = LocationRestaurant().apply {
                coordinates =coordinatesList
            }
        }
        viewModel.addRestaurant(restaurantRequest)
        viewModel.restaurantResponse.observe(viewLifecycleOwner) { restaurantResponse ->
            // Handle the restaurantResponse here
            Log.d("RestaurantFragment", "Response: $restaurantResponse")
        }
    }
}