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
    val hotelRequests: List<Any>,
    val id: String,
    val name: String,
    val password: String,
    val restaurantRequests: List<Any>,
    val role: String
)
data class Data(
    val user: User
)