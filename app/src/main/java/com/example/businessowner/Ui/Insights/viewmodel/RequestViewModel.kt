package com.example.businessowner.Ui.Insights.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.businessowner.Network.repository.RequestRepository
import com.example.businessowner.model.getRespond.hotel.Hotel
import com.example.businessowner.model.getRespond.restaurant.RestaurantReview
import com.example.businessowner.model.getRespond.hotel.ReviewHotel
import com.example.businessowner.model.getRespond.restaurant.Restaurant
import com.example.businessowner.model.getRespond.restaurant.ReviewRes
//import com.example.businessowner.model.neww.ReviewHotelNew
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class RequestViewModel @Inject constructor(
    private val requestRepository: RequestRepository
):ViewModel() {

    private val _getRestaurantResponseLiveData= MutableLiveData<List<Restaurant>>()
    val getRestaurantResponseLiveData:LiveData<List<Restaurant>> = _getRestaurantResponseLiveData
            fun getRestaurantResponse()=viewModelScope.launch {
                try {
                    val response=requestRepository.getRestaurantRespond()
                    if (response.isSuccessful){
                        response.body()!!.data.restaurants.let {
                            _getRestaurantResponseLiveData.postValue(it)
                            Log.e("Great response  ", "getRestaurantResponse success ")

                        }
                    }else {
                        val errorBody = response.errorBody()?.string()
                        val statusCode = response.code()
                        Log.e(
                            "requestViewModel",
                            "Failed to give getRestaurantResponse. Error body: $errorBody, Status code: $statusCode"
                        )
                    }
                }catch (e: Exception){
                    Log.e("PlaceViewModel", "Failed to give getRestaurantResponse. Exception: ${e.message}")
                }
            }


        private val _getHotelRespondLiveData=MutableLiveData<List<Hotel>>()
    val getHotelResponseLiveData:LiveData<List<Hotel>> = _getHotelRespondLiveData
    fun getHotelResponse()=viewModelScope.launch {
        try {
            val response=requestRepository.getHotelRespond()
            if (response.isSuccessful){
                response.body()!!.data.hotels.let {
                    _getHotelRespondLiveData.postValue(it)
                    Log.e("Great response  ", "getHotelResponse success ")
                }
            }else {
                val errorBody = response.errorBody()?.string()
                val statusCode = response.code()
                Log.e(
                    "requestViewModel",
                    "Failed to give getHotelResponse. Error body: $errorBody, Status code: $statusCode"
                )
            }
        }catch (e: Exception){
            Log.e("PlaceViewModel", "Failed to give getHotelResponse. Exception: ${e.message}")
        }
    }


    private val _getRestaurantReviewsLiveData= MutableLiveData<List<ReviewRes>>()
    val getRestaurantReviewsLiveData:LiveData<List<ReviewRes>> = _getRestaurantReviewsLiveData
    fun getRestaurantReviews(resId:String)=viewModelScope.launch {
        try {
            val response=requestRepository.getRestaurantReviews(resId)
            if (response.isSuccessful){
                response.body()!!.data?.restaurant?.reviews.let {
                    _getRestaurantReviewsLiveData.postValue(it)
                    Log.e("great response","getRestaurantReviews success")
                }
            }else {
                val errorBody = response.errorBody()?.string()
                val statusCode = response.code()
                Log.e(
                    "requestViewModel",
                    "Failed to give getRestaurantReviews. Error body: $errorBody, Status code: $statusCode"
                )
            }
        }catch (e: Exception){
            Log.e("PlaceViewModel", "Failed to give getRestaurantReviews. Exception: ${e.message}")
        }
    }

    private val _getHotelReviewsLiveData = MutableLiveData<List<ReviewHotel>>()
    val getHotelReviewsLiveData:LiveData<List<ReviewHotel>> = _getHotelReviewsLiveData
    fun getHotelReviews(HotelId:String)=viewModelScope.launch {
        try {
            val response=requestRepository.getHotelReviews(HotelId)
            if (response.isSuccessful){
                response.body()!!.data?.reviews.let {
                    _getHotelReviewsLiveData.postValue(it)
                    Log.e("success reviews","successHotelReviews")
                   // Log.e("success reviews",it.toString())
                }
            }else {
                val errorBody = response.errorBody()?.string()
                val statusCode = response.code()
                Log.e(
                    "requestViewModel",
                    "Failed to give getHotelReviews. Error body: $errorBody, Status code: $statusCode"
                )
            }
        }catch (e: Exception){
            Log.e("PlaceViewModel", "Failed to give getHotelReviews. Exception: ${e.message}")
        }
    }




}