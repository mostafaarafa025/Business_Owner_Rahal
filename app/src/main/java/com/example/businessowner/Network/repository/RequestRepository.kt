package com.example.businessowner.Network.repository

import com.example.businessowner.Network.remote.HomeApi
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RequestRepository @Inject constructor(
    private val homeApi: HomeApi
) {
        suspend fun getHotelRequest(id:String)=homeApi.getHotelRequest(id)

        suspend fun getRestaurantRequest(idRes: String)=homeApi.getRestaurantRequest(idRes)
}