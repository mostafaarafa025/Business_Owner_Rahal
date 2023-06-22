package com.example.businessowner.model.getRespond.restaurant

data class RestaurantReviewsNew(
    val `data`: DataResReview?=null,
    val status: String
)

data class DataResReview(
    val restaurant: RestaurantReview
)
data class RestaurantReview(
    val reviews: List<ReviewRes>,
)

data class ReviewRes(
    val __v: Int,
    val _id: String,
    val comment: String,
    val createdAt: String,
    val id: String,
    val rating: Double,
    val restaurant: String,
    val userId: UserId
)

data class UserId(
    val _id: String,
    val id: String,
    val name: String
)