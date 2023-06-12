package com.example.businessowner.model.authentication

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class SignUpRequest {
    var name :String?=null

    var email: String?=null

    var password: String?=null

    var passwordConfirm: String?=null

    var role: String?=null
}