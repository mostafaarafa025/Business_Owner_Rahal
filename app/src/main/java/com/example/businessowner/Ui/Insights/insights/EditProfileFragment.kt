package com.example.businessowner.Ui.Insights.insights

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.format.DateFormat
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.navigation.findNavController

import com.example.businessowner.R
import com.example.businessowner.Ui.Insights.Signup.PhotoAdapter
import com.example.businessowner.Ui.Insights.Signup.SignUp1
import com.example.businessowner.Ui.Insights.viewmodel.PlaceViewModel
import com.example.businessowner.databinding.FragmentEditProfileBinding
import com.example.businessowner.databinding.HotelLayoutBinding
import com.example.businessowner.databinding.RestaurantLayoutBinding
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class EditProfileFragment : Fragment() {
    lateinit var binding:FragmentEditProfileBinding
    val imageUrls = mutableListOf<String>()
//    private var coordinatesList: List<Double> = listOf(2.5, 45.2)
    val images = mutableListOf<Uri>()
    companion object {
        const val PICK_IMAGE_REQUEST_ID = 103
    }
private var restaurantLayoutBinding: RestaurantLayoutBinding? = null
    private var hotelLayoutBinding: HotelLayoutBinding? = null
    private var selectedStartTimeString: String? = null
    private var selectedEndTimeString: String? = null
   // private var imageUrls: List<String> = emptyList()
   // private var coordinatesList: List<Double> = emptyList()
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
    private var selectedStartTime: Double? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
       binding= FragmentEditProfileBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        categoryDropDown()
        val governments=resources.getStringArray(R.array.Government)
        val arrayAdapter= ArrayAdapter(requireContext(), R.layout.dropdown_item,governments)
        binding.governmentSpinnerAutoComplete.setAdapter(arrayAdapter)

        binding.addImageId.setOnClickListener {
            pickImageGallery()
        }
        binding.submitButton.setOnClickListener {

            Toast.makeText(requireContext(), "Place Added", Toast.LENGTH_SHORT).show()
            CoroutineScope(Dispatchers.Main).launch {
                delay(2000)
                view?.findNavController()
                    ?.navigate(R.id.action_editProfileFragment_to_loadingNewPlaceFragment)
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


    private fun pickImageGallery() {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "image/*"
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
        startActivityForResult(
            Intent.createChooser(intent, "Select Picture"),
            EditProfileFragment.PICK_IMAGE_REQUEST_ID
        )
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK) {
            if (data?.clipData != null) {
                val count = data.clipData!!.itemCount
                for (i in 0 until count) {
                    val imageUri = data.clipData!!.getItemAt(i).uri
                    images.add(imageUri)
                }
            } else if (data?.data != null) {
                val imageUri = data.data!!
                images.add(imageUri)
            }
            for (imageUri in images) {
                val imageUrl = imageUri.toString()
                imageUrls.add(imageUrl)
            }
        }
        binding.imageRecyclerView.adapter = PhotoAdapter(images)
        if (images.isNotEmpty()){
            binding.exImage1.visibility=View.GONE
            binding.exImage2.visibility=View.GONE
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


    }

    private fun showHotelLayout() {
        if (hotelLayoutBinding == null) {
            hotelLayoutBinding = HotelLayoutBinding.inflate(layoutInflater)
        }
        binding.frameLayoutCategory.removeAllViews()
        binding.frameLayoutCategory.addView(hotelLayoutBinding!!.root)
    }
    private fun openTimePickerFrom() {
        val isSystem24Hour = DateFormat.is24HourFormat(requireContext())
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
        val isSystem24Hour = DateFormat.is24HourFormat(requireContext())
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
            //String.format("%02d\\:%02d", selectedHour, selectedMinute)
            // Update the selected time in the spinner
            restaurantLayoutBinding?.editTextTimeTo?.setText(selectedEndTimeString.toString())
            Log.e("timeEnd", selectedStartTimeString.toString())
        }
    }

}