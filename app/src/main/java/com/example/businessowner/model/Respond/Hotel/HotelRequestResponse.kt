package com.example.businessowner.model.Respond.Hotel

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class HotelRequestResponse(
    val `data`: Data,
    val status: String
):Parcelable

@Parcelize
data class Data(
    val document: Document
):Parcelable

@Parcelize
data class Document(
    val Description: String,
    val __v: Int,
    val _id: String,
    val address: String,
    val hotelClass: Int,
    val hotelRequests: List<String>,
    val id: String,
    val image: List<String>,
    val location: Location,
    val name: String,
    val numberOfReviews: Int,
    val phone: String,
    val priceLevel: String,
    val rating: Double,
    val reviews: List<String>,
    val status: String
):Parcelable

@Parcelize
data class Location(
    val coordinates: List<Double>,
    val type: String
):Parcelable
