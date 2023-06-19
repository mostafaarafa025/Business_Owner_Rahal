package com.example.businessowner.model.getRespond.restaurant

data class RestaurantReviews(
    val `data`: DataRestaurantReviews,
    val status: String
)

data class DataRestaurantReviews(
    val reviews: List<Review>
)
data class Review(
    val __v: Int,
    val _id: String,
    val comment: String,
    val createdAt: String,
    val id: String,
    val name:String,
    var rating: Double,
    val restaurant: String,
    val userId: String
)