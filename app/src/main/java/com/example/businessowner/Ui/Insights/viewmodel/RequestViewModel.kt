package com.example.businessowner.Ui.Insights.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.businessowner.Network.repository.RequestRepository
import com.example.businessowner.model.Respond.Hotel.Document
import com.example.businessowner.model.Respond.Restaurant.DocumentRes
import com.example.businessowner.model.Respond.Restaurant.Review
import com.example.businessowner.model.getRespond.restaurant.Restaurant
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class RequestViewModel @Inject constructor(
    private val requestRepository: RequestRepository
):ViewModel() {
    private val _getHotelRequestLiveData=MutableLiveData<List<Document>>()
    val getHotelRequestLiveData:LiveData<List<Document>> = _getHotelRequestLiveData

    fun getHotelRequest(id:String)= viewModelScope.launch {
        try {
        val response=requestRepository.getHotelRequest(id)
            if (response.isSuccessful){
                response.body()!!.data.document.let {
            _getHotelRequestLiveData.postValue(listOf(it))
                    Log.e("Great request  ", "HotelResponse success ")
                }
            }   else {
                val errorBody = response.errorBody()?.string()
                val statusCode = response.code()
                Log.e(
                    "requestViewModel",
                    "Failed to give HotelResponse. Error body: $errorBody, Status code: $statusCode"
                )

            }

        }catch (e: Exception){
            Log.e("PlaceViewModel", "Failed to give HotelResponse. Exception: ${e.message}")
        }
    }
    private val _getRestaurantLiveData=MutableLiveData<List<DocumentRes>>()
    val getRestaurantLiveData:LiveData<List<DocumentRes>> = _getRestaurantLiveData
            fun getRestaurantRequest(idRes: String)=viewModelScope.launch {
                try {
                    val response=requestRepository.getRestaurantRequest(idRes)
                    if (response.isSuccessful){
                        response.body()!!.data.document.let {
                            _getRestaurantLiveData.postValue(listOf(it))
                            Log.e("Great request  ", "RequestResponse success ")
                        }
                    }else {
                        val errorBody = response.errorBody()?.string()
                        val statusCode = response.code()
                        Log.e(
                            "requestViewModel",
                            "Failed to give RestaurantRequest. Error body: $errorBody, Status code: $statusCode"
                        )
                    }
                }catch (e: Exception){
                    Log.e("PlaceViewModel", "Failed to give RestaurantRequest. Exception: ${e.message}")
                }
            }
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
    private val _getRestaurantReviewsLiveData= MutableLiveData<List<Review>>()
    val getRestaurantReviewsLiveData:LiveData<List<Review>> = _getRestaurantReviewsLiveData
    fun getRestaurantReviews(resId:String)=viewModelScope.launch {
        try {
            val response=requestRepository.getRestaurantReviews(resId)
            if (response.isSuccessful){
                response.body()!!.data.reviews.let {
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

}