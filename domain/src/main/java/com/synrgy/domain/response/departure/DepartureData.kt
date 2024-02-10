package com.synrgy.domain.response.departure


import com.google.gson.annotations.SerializedName

data class DepartureData(
    @SerializedName("airport")
    val airport: AirportItem?,
    @SerializedName("createdAt")
    val createdAt: Long?,
    @SerializedName("id")
    val id: Int?,
    @SerializedName("scheduledTime")
    val scheduledTime: Long?,
    @SerializedName("terminal")
    val terminal: String?,
    @SerializedName("updatedAt")
    val updatedAt: Any?
)