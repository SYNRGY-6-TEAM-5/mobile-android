package com.synrgy.domain.response.airport


import com.google.gson.annotations.SerializedName

data class AirportData(
    @SerializedName("airPortName")
    val airPortName: String?,
    @SerializedName("cityIataCode")
    val cityIataCode: String?,
    @SerializedName("cityName")
    val cityName: String?,
    @SerializedName("countryIsoCode")
    val countryIsoCode: String?,
    @SerializedName("countryName")
    val countryName: String?,
    @SerializedName("createdAt")
    val createdAt: Long?,
    @SerializedName("gmt")
    val gmt: String?,
    @SerializedName("iataCode")
    val iataCode: String?,
    @SerializedName("id")
    val id: Int?,
    @SerializedName("updatedAt")
    val updatedAt: Any?
)