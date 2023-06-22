package com.example.businessowner.Ui.Insights.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.businessowner.model.Respond.Hotel.Document
import com.example.businessowner.model.Respond.Restaurant.DocumentRes
import com.example.businessowner.model.getRespond.restaurant.Restaurant
import com.example.businessowner.model.getRespond.restaurant.RestaurantReview

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
    private val _getRestaurantResponseLiveDataShared=MutableLiveData<List<RestaurantReview>>()
    val getRestaurantResponseLiveDataShared:LiveData<List<RestaurantReview>> = _getRestaurantResponseLiveDataShared

    fun sendRestaurantResponse(restaurant: List<RestaurantReview>){
        _getRestaurantResponseLiveDataShared.value=restaurant
    }

    private val _restaurantLiveDataShared=MutableLiveData<List<Restaurant>>()
    val restaurantLiveDataShared:LiveData<List<Restaurant>> = _restaurantLiveDataShared
    fun saveRestaurantData(RestaurantData:List<Restaurant>){
        _restaurantLiveDataShared.value=RestaurantData
    }
}