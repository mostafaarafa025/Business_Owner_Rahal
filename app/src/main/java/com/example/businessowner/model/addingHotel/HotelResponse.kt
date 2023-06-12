package com.example.businessowner.model.addingHotel

data class HotelResponse(
    val `data`: Data,
    val status: String
)

data class Data(
    val document: Document
)

data class Document(
    val Description: String,
    val __v: Int,
    val _id: String,
    val address: String,
    val hotelClass: Int,
    val hotelRequests: List<Any>,
    val id: String,
    val image: List<String>,
    val location: Location,
    val name: String,
    val numberOfReviews: Int,
    val phone: String,
    val priceLevel: String,
    val rating: Double,
    val reviews: List<Any>,
    val status: String
)
data class Location(
    val coordinates: List<Double>,
    val type: String
)