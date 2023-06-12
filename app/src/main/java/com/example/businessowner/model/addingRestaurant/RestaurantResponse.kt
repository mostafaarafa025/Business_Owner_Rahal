package com.example.businessowner.model.addingRestaurant

data class RestaurantResponse(
    val `data`: Data,
    val status: String
)
data class Data(
    val document: Document
)

data class Document(
    val Description: String,
    val StartingTime: Double,
    val __v: Int,
    val _id: String,
    val closeAt: Double,
    val cuisine: List<Any>,
    val id: String,
    val image: List<String>,
    val location: LocationR,
    val name: String,
    val numberOfReviews: Int,
    val phone: String,
    val priceLevel: String,
    val rating: Double,
    val reviews: List<Any>,
    val status: String,
    val workingDays: String
)
data class LocationR(
    val coordinates: List<Double>,
    val type: String
)