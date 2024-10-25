package com.example.myapplication3.models

data class TouristSpot(val name: String, val description: String)
data class Restaurant(val name: String, val cuisine: String)
data class Accommodation(val name: String, val type: String)

data class ItineraryItem(
    val day: String,   // e.g., "Day 1"
    val time: String,  // e.g., "10:00 AM"
    val place: String  // e.g., "Eiffel Tower"
)
