package com.example.businessowner.Network.remote

import com.example.businessowner.model.Respond.Hotel.HotelRequestResponse
import com.example.businessowner.model.Respond.Restaurant.RestaurantRequestResponse
import com.example.businessowner.model.getRespond.restaurant.RestaurantReviews
import com.example.businessowner.model.addingHotel.HotelRequest
import com.example.businessowner.model.addingHotel.HotelResponse
import com.example.businessowner.model.addingRestaurant.RestaurantRequest
import com.example.businessowner.model.addingRestaurant.RestaurantResponse
import com.example.businessowner.model.authentication.LoginRequest
import com.example.businessowner.model.authentication.SignUpRequest
import com.example.businessowner.model.authentication.SignUpResponse
import com.example.businessowner.model.getRespond.hotel.HotelReviews
import com.example.businessowner.model.getRespond.hotel.getHotelRespond
import com.example.businessowner.model.getRespond.restaurant.getRestaurantRespond
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path

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




    @GET("hotels/{id}")
    suspend fun getHotelRequest(
    @Path("id") id:String
    ):Response<HotelRequestResponse>

    @GET("restaurants/{idRes}")
    suspend fun getRestaurantRequest(
        @Path("idRes") id:String
    ):Response<RestaurantRequestResponse>


    @GET("restaurants")
    suspend fun getRestaurantRespond(
        @Header("Authorization") token: String
    ):Response<getRestaurantRespond>

    @GET("hotels")
    suspend fun getHotelRespond(
        @Header("Authorization") token: String
    ):Response<getHotelRespond>

    @GET("restaurants/restaurantReviews/{resId}")
    suspend fun getRestaurantReviews(
        @Header("Authorization") token: String,
        @Path("resId") id: String
    ):Response<RestaurantReviews>

    @GET("hotels/hotelreviews/{hotelId}")
    suspend fun getHotelReviews(
        @Header("Authorization") token: String,
        @Path("hotelId") id: String
    ):Response<HotelReviews>
}




//    @POST("users/forgetpassword")
//    fun forgetPassword(
//        @Body forgetPasswordRequest: ForgetPasswordRequest
//    )
