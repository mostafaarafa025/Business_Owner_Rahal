package com.example.businessowner.model.authentication

data class SignUpResponse(
    val `data`: Data,
    val status: String,
    val token: String
)

data class User(
    val __v: Int,
    val _id: String,
    val active: Boolean,
    val email: String,
    val id: String,
    val name: String,
    val password: String,
    val role: String
)

data class Data(
    val user: User
)