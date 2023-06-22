package com.example.businessowner.model.getRespond.hotel

data class HotelReviewsNews(
    val `data`: DataReview?=null,
    val status: String
)

data class DataReview(
    val reviews: List<ReviewHotel>
)

data class ReviewHotel(
    val __v: Int,
    val _id: String,
    val comment: String,
    val createdAt: String,
    val hotel: String,
    val id: String,
    val rating: Int,
    val userId: UserId
)

data class UserId(
    val _id: String,
    val id: String,
    val name: String
)