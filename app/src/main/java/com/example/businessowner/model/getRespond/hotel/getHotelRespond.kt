package com.example.businessowner.model.getRespond.hotel

data class getHotelRespond(
    val `data`: Data,
    val results: Int,
    val status: String
)


data class Data(
    val hotels: List<Hotel>
)


data class Hotel(
    val Description: String,
    val __v: Int,
    val _id: String,
    val address: String,
    val createdBy: String,
    val hotelClass: Int,
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