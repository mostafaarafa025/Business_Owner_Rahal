package com.example.businessowner.Ui.Insights.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.businessowner.Network.repository.AddingRepository
import com.example.businessowner.model.addingHotel.HotelRequest
import com.example.businessowner.model.addingHotel.HotelResponse
import com.example.businessowner.model.addingRestaurant.RestaurantRequest
import com.example.businessowner.model.addingRestaurant.RestaurantResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PlaceViewModel @Inject constructor(
    private val addingRepository: AddingRepository
) : ViewModel()  {

    private val _hotelResponse = MutableLiveData<HotelResponse>()
    val hotelResponse: LiveData<HotelResponse> = _hotelResponse

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    fun addHotel(placeRequest: HotelRequest){
        viewModelScope.launch {
            try {

                val response = addingRepository.addPlace(placeRequest)
                if (response.isSuccessful) {
                    if (response.isSuccessful) {
                        _hotelResponse.value = response.body()
                        Log.e("PlaceViewModel", "Place added successfully: ${response.body()}")
                    }
                } else {
                    val errorBody = response.errorBody()?.string()
                    val statusCode = response.code()
                    Log.e(
                        "PlaceViewModel",
                        "Failed to add hotel. Error body: $errorBody, Status code: $statusCode"
                    )
                    _error.value = "Failed to add place"
                }
            }catch (e: Exception){
                Log.e("PlaceViewModel", "Failed to add hotel. Exception: ${e.message}")
                _error.value = "Failed to add place"
            }
        }
    }

    private val _restaurantResponse = MutableLiveData<RestaurantResponse>()
    val restaurantResponse: LiveData<RestaurantResponse> = _restaurantResponse

    fun addRestaurant(restaurantRequest: RestaurantRequest){
        viewModelScope.launch {
            try {
                val response = addingRepository.addRestaurant(restaurantRequest)
                if (response.isSuccessful) {
                    _restaurantResponse.value = response.body()
                    Log.e("PlaceViewModel", "Restaurant added successfully: ${response.body()}")
                } else {
                    val errorBody = response.errorBody()?.string()
                    val statusCode = response.code()
                    Log.e(
                        "PlaceViewModel",
                        "Failed to add restaurant. Error body: $errorBody, Status code: $statusCode"
                    )
                    _error.value = "Failed to add place"
                }
            }catch (e: Exception){
                Log.e("PlaceViewModel", "Failed to add restaurant. Exception: ${e.message}")
                _error.value = "Failed to add place"
            }
        }
            }
    }

