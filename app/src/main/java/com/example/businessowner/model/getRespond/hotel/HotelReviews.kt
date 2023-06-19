package com.example.businessowner.model.getRespond.hotel

data class HotelReviews(
    val `data`: DataHotelReviews,
    val status: String
)

data class DataHotelReviews(
    val reviews: List<ReviewHotel>
)
data class ReviewHotel(
    val __v: Int,
    val _id: String,
    val comment: String,
    val createdAt: String,
    val name:String,
    val hotel: String,
    val id: String,
    val rating: Double,
    val userId: String
)