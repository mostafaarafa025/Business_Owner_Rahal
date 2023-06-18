package com.example.businessowner.Ui.Insights.Signup

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import com.example.businessowner.R
import com.example.businessowner.Ui.Insights.viewmodel.PlaceViewModel
import com.example.businessowner.databinding.FragmentSignUp1Binding
import com.example.businessowner.model.addingHotel.HotelRequest
import com.example.businessowner.model.addingHotel.LocationHotel
import com.example.businessowner.model.addingRestaurant.LocationRestaurant
import com.example.businessowner.model.addingRestaurant.RestaurantRequest
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignUp1 : Fragment() {
    lateinit var binding: FragmentSignUp1Binding
    private val viewModel: PlaceViewModel by viewModels()
    val imageUrls = mutableListOf<String>()
    private var coordinatesList: List<Double> = listOf(2.5, 45.2)
    val images = mutableListOf<Uri>()
    companion object {
        const val PICK_IMAGE_REQUEST = 101
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding=FragmentSignUp1Binding.inflate(inflater,container,false)
return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.floatingButton.setOnClickListener {
          sendData()
        }
        val governments=resources.getStringArray(R.array.Government)
        val arrayAdapter=ArrayAdapter(requireContext(), R.layout.dropdown_item,governments)
        binding.governmentSpinnerAutoComplete.setAdapter(arrayAdapter)

        binding.addImageId.setOnClickListener {
            pickImageGallery()
        }
        binding.logInTextVeiw.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.loginFragment)
        }

    }

    private fun takeCoordinates(){
    val coordinatesText = binding.mapLocationEditText.text.toString()
    val regex = Regex("@(-?\\d+\\.\\d+),(-?\\d+\\.\\d+)")
    val matchResult = regex.find(coordinatesText)
    val (latitude, longitude) = matchResult?.destructured?.toList() ?: listOf("0", "0")
     coordinatesList = listOf(latitude.toDouble(), longitude.toDouble())

    }
    private fun sendData(){
        takeCoordinates()
        val government=binding.governmentSpinnerAutoComplete.text.toString()
        val fullAddress=binding.fullAddressEditText.text.toString()

            var bundle= Bundle().apply {
               putString("government",government)
         putString("fullAddress",fullAddress)
                putStringArrayList("imageUrls", ArrayList(imageUrls))
        putDoubleArray("coordinates", coordinatesList.toDoubleArray())
            }
        val fragment=SignUp2()
        fragment.arguments=bundle
   view?.findNavController()?.navigate(R.id.action_signUp1_to_signUp2,bundle)

        Log.d("Coordinates", government)
        Log.d("Coordinates", fullAddress)
        Log.d("Coordinates", coordinatesList.toString())

    }

    private fun pickImageGallery() {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "image/*"
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST)
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


}