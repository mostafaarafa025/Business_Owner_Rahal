package com.example.businessowner.Network.repository

import com.example.businessowner.Network.remote.HomeApi
import com.example.businessowner.model.addingHotel.HotelRequest
import com.example.businessowner.model.addingHotel.HotelResponse
import com.example.businessowner.model.addingRestaurant.RestaurantRequest
import com.example.businessowner.model.addingRestaurant.RestaurantResponse
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AddingRepository  @Inject constructor(
    private val homeApi: HomeApi,
    private val authRepository: AuthRepository
) {

    suspend fun addPlace(placeRequest: HotelRequest): Response<HotelResponse> {
        val authToken = authRepository.getAuthToken()
        return homeApi.addingPlace(placeRequest,"Bearer $authToken")
    }
   suspend fun addRestaurant(restaurantRequest: RestaurantRequest):Response<RestaurantResponse>{
       val authToken = authRepository.getAuthToken()
       return homeApi.addingRestaurant(restaurantRequest,"Bearer $authToken")
   }

}