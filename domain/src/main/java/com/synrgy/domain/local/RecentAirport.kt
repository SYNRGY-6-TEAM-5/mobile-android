package com.synrgy.domain.local

data class RecentAirport(
    val id: Int,
    val airPortName: String?,
    val cityIataCode: String?,
    val cityName: String?,
    val countryIsoCode: String?,
    val countryName: String?,
    val gmt: String?,
    val iataCode: String?
)
