package com.example.businessowner.model.getRespond.restaurant

data class getRestaurantRespond(
    val `data`: Data,
    val results: Int,
    val status: String
)

data class Data(
    val restaurants: List<Restaurant>
)

data class Restaurant(
    val __v: Int,
    val Description: String,
    val _id: String,
    val address: String,
    val createdBy: String,
    val cuisine: List<Any>,
    val StartingTime:String,
    val workingDays:String,
    val closeAt:String,
    var id: String,
    val image: List<String>,
    val location: Location,
    var name: String,
    val numberOfReviews: Int,
    var phone: String,
    val priceLevel: String,
    val rating: Double,
    val reviews: List<Any>,
    val status: String
)

data class Location(
    val coordinates: List<Double>,
    val type: String
)