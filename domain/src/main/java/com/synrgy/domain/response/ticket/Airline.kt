package com.synrgy.domain.response.ticket


import com.google.gson.annotations.SerializedName

data class Airline(
    @SerializedName("airline_id")
    val airlineId: Int?,
    @SerializedName("created_at")
    val createdAt: String?,
    @SerializedName("iata")
    val iata: String?,
    @SerializedName("image")
    val image: String?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("updated_at")
    val updatedAt: String?
)