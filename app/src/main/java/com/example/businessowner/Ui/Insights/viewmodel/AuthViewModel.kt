package com.example.businessowner.Ui.Insights.viewmodel

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.businessowner.Network.repository.AuthRepository
import com.example.businessowner.model.authentication.LoginRequest
import com.example.businessowner.model.authentication.SignUpRequest
import com.example.businessowner.model.authentication.SignUpResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _signUpResponse = MutableLiveData<SignUpResponse>()
    val signUpResponse: LiveData<SignUpResponse> = _signUpResponse

fun signUp( request: SignUpRequest) {
    viewModelScope.launch {
        try {
            val response = authRepository.signUp(request)
            if (response.isSuccessful) {
                _signUpResponse.value = response.body()
                Log.e("good Signup","signup :good")
            }

        } catch (e: Exception) {
            Log.e("error Signup","signup : ${e.message}")
        }
    }
}
    private val _loginResponse = MutableLiveData<SignUpResponse>()
    val loginResponse: LiveData<SignUpResponse> = _loginResponse

    fun login(requestLogin:LoginRequest){
    viewModelScope.launch {
        try {
            val response=authRepository.login(requestLogin)
            if (response.isSuccessful &&response.body()?.data?.user?.role=="business_owner"){
                _loginResponse.value=response.body()
                Log.e("good Login","Login :good")
            }
        }catch (e: Exception) {
            Log.e("error Login","login : ${e.message}")
        }
    }
    }


}

