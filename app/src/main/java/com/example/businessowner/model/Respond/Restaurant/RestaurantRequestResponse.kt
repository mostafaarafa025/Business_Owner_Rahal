package com.example.businessowner.model.Respond.Restaurant

data class RestaurantRequestResponse(
    val `data`: DataRes,
    val status: String
)

data class DataRes(
    val document: DocumentRes
)

data class DocumentRes(
    val Description: String,
    val StartingTime: Double,
    val __v: Int,
    val _id: String,
    val address: String,
    val closeAt: Double,
    val cuisine: List<String>,
    val id: String,
    val image: List<Any>,
    val location: LocationRes,
    val name: String,
    val numberOfReviews: Int,
    val phone: String,
    val priceLevel: String,
    val rating: Double,
    val reviews: List<Any>,
    val status: String,
    val workingDays: String
)

data class LocationRes(
    val coordinates: List<Double>,
    val type: String
)