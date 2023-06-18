package com.example.businessowner.model.Respond.Restaurant

data class RestaurantReviews(
    val `data`: DataReviews,
    val status: String
)

data class DataReviews(
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