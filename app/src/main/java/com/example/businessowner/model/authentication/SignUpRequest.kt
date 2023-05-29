package com.example.businessowner.model.authentication

import com.google.gson.annotations.SerializedName

data class SignUpRequest(
    @SerializedName("email")
    var email: String? = null,

    @SerializedName("name")
    var name: String? = null,

    @SerializedName("password")
    var password: String? = null,

    @SerializedName("passwordConfirm")
    var passwordConfirm: String? = null,

    @SerializedName("role")
    var role: String? = null
)