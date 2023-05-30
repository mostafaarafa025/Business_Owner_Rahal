package com.example.businessowner.Network.remote

import com.example.businessowner.model.authentication.SignUpRequest
import com.example.businessowner.model.authentication.SignUpResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface HomeApi {
    @POST("users/signup")
  suspend  fun signup(
        @Body registerUserRequest: SignUpRequest
    ): Response<SignUpResponse>
}
//    @POST("users/login")
//    fun login(
//        @Body userRequest: UserRequest
//    ): Call<SignupResponse>
//

//    @POST("users/forgetpassword")
//    fun forgetPassword(
//        @Body forgetPasswordRequest: ForgetPasswordRequest
//    )
