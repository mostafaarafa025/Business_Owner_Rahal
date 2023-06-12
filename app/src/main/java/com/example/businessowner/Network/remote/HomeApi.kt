package com.example.businessowner.Network.remote

import com.example.businessowner.model.addingHotel.HotelRequest
import com.example.businessowner.model.addingHotel.HotelResponse
import com.example.businessowner.model.addingRestaurant.RestaurantRequest
import com.example.businessowner.model.addingRestaurant.RestaurantResponse
import com.example.businessowner.model.authentication.LoginRequest
import com.example.businessowner.model.authentication.SignUpRequest
import com.example.businessowner.model.authentication.SignUpResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST

interface HomeApi {
    @POST("users/signup")
  suspend  fun signup(
        @Body registerUserRequest: SignUpRequest
    ): Response<SignUpResponse>

    @POST("users/login")
   suspend fun login(
        @Body userRequest: LoginRequest
    ): Response<SignUpResponse>

    @POST("hotels")
    suspend fun addingPlace(
        @Body placeRequest: HotelRequest,
        @Header("Authorization") token: String

    ):Response<HotelResponse>


 @POST("restaurants")
    suspend fun addingRestaurant(
        @Body restaurantRequest: RestaurantRequest,
        @Header("Authorization") token: String
    ):Response<RestaurantResponse>



//    @GET("hotels/{id}")
//    suspend fun getRestaurantRequest(
//
//    )
}


//    @POST("users/forgetpassword")
//    fun forgetPassword(
//        @Body forgetPasswordRequest: ForgetPasswordRequest
//    )
