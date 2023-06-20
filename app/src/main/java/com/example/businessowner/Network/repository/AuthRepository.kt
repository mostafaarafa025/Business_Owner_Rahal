package com.example.businessowner.Network.repository

import android.content.Context
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.businessowner.Network.remote.HomeApi
import com.example.businessowner.model.authentication.LoginRequest
import com.example.businessowner.model.authentication.SignUpRequest
import com.example.businessowner.model.authentication.SignUpResponse
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthRepository @Inject constructor(
    private val homeApi: HomeApi,
    private val context: Context
){

    private var authToken: String? = null
    suspend fun signUp(request: SignUpRequest)=homeApi.signup(request)

    suspend fun login(requestLogin: LoginRequest): Response<SignUpResponse> {
        val response = homeApi.login(requestLogin)
        if (response.isSuccessful) {
            authToken = response.body()?.token
            Log.e("token",authToken.toString())
        }
        return response
    }

    fun getAuthToken(): String? {
        return authToken
    }



}
