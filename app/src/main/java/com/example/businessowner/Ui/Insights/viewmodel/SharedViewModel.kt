package com.example.businessowner.Ui.Insights.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.businessowner.model.Respond.Hotel.Document
import com.example.businessowner.model.Respond.Restaurant.DocumentRes
import com.example.businessowner.model.getRespond.restaurant.Restaurant

class SharedViewModel:ViewModel  ()  {
    private val _hotelRequestLiveDataShared = MutableLiveData<List<Document>>()
    val hotelRequestLiveDataShared: LiveData<List<Document>> = _hotelRequestLiveDataShared

    fun setHotelRequest(documentsHotel: List<Document>) {
        _hotelRequestLiveDataShared.value = documentsHotel
    }


    private val _restaurantRequestLiveDataShared = MutableLiveData<List<DocumentRes>>()
    val restaurantRequestLiveDataShared: LiveData<List<DocumentRes>> = _restaurantRequestLiveDataShared

        fun sendRestaurantRequest(documentsRes:List<DocumentRes>){
            _restaurantRequestLiveDataShared.value=documentsRes
        }
    private val _getRestaurantResponseLiveDataShared=MutableLiveData<List<Restaurant>>()
    val getRestaurantResponseLiveDataShared:LiveData<List<Restaurant>> = _getRestaurantResponseLiveDataShared

    fun sendRestaurantResponse(restaurant: List<Restaurant>){
        _getRestaurantResponseLiveDataShared.value=restaurant
    }
}