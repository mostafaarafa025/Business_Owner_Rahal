package com.example.businessowner.model.addingRestaurant

 class RestaurantRequest{
    var Description: String?=null
    var StartingTime: String?=null
    var address: String?=null
    var city: String?=null
    var closeAt: String?=null
    var cuisine: List<String>?=null
    var image: List<String>?=null
    var location: LocationRestaurant?=null
    var name: String?=null
    var phone: String?=null
    var priceLevel: String?=null
    var rating: Double?=null
    var workingDays: String ?=null

}
 class LocationRestaurant{
     var coordinates: List<Double>?=null

 }
