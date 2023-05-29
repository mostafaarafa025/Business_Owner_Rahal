package com.example.businessowner.Network.remote

import com.example.businessowner.model.authentication.SignUpRequest
import com.example.businessowner.model.authentication.SignUpResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface HomeApi {

//    @POST("users/login")
//    fun login(
//        @Body userRequest: UserRequest
//    ): Call<SignupResponse>
//

    @POST("users/signup")
    fun signup(
        @Body registerUserRequest: SignUpRequest
    ): Call<SignUpResponse>


//    @POST("users/forgetpassword")
//    fun forgetPassword(
//        @Body forgetPasswordRequest: ForgetPasswordRequest
//    )
}