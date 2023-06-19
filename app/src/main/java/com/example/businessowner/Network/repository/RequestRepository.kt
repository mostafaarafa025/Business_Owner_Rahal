package com.example.businessowner.Network.repository

import com.example.businessowner.Network.remote.HomeApi
import javax.inject.Inject
import javax.inject.Singleton

    @Singleton
    class RequestRepository @Inject constructor(
        private val authRepository: AuthRepository,
        private val homeApi: HomeApi
    ) {
        val authToken = authRepository.getAuthToken()
            suspend fun getHotelRequest(id:String)=homeApi.getHotelRequest(id)

            suspend fun getRestaurantRequest(idRes: String)=homeApi.getRestaurantRequest(idRes)

            suspend fun getRestaurantRespond()=homeApi.getRestaurantRespond("Bearer $authToken")

            suspend fun getHotelRespond()=homeApi.getHotelRespond("Bearer $authToken")

            suspend fun getRestaurantReviews(resId:String)=homeApi.getRestaurantReviews("Bearer $authToken",resId)

            suspend fun getHotelReviews(hotelId:String)=homeApi.getHotelReviews("Bearer $authToken",hotelId)
    }