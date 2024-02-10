package com.synrgy.domain.response.flight


import com.google.gson.annotations.SerializedName

data class AirportDetails(
    @SerializedName("airport_id")
    val airportId: Int?,
    @SerializedName("airport_name")
    val airportName: String?,
    @SerializedName("city_iata_code")
    val cityIataCode: String?,
    @SerializedName("city_name")
    val cityName: String?,
    @SerializedName("country_iso_code")
    val countryIsoCode: String?,
    @SerializedName("country_name")
    val countryName: String?,
    @SerializedName("created_at")
    val createdAt: String?,
    @SerializedName("gmt")
    val gmt: String?,
    @SerializedName("iata_code")
    val iataCode: String?,
    @SerializedName("updated_at")
    val updatedAt: Any?
)