package com.example.businessowner.model.addingHotel

import kotlinx.parcelize.Parcelize


 class HotelRequest{
     var Description: String?=null
     var address: String?=null
     var city: String?=null
     var hotelClass: Int?=null
     var image: List<String>?=null
     var location: LocationHotel?=null
     var name: String?=null
     var numberOfReviews: Int?=null
     var phone: String?=null
     var priceLevel: String?=null
     var rating: Double?=null

 }

 class LocationHotel{
     var coordinates: List<Double>?=null

 }
