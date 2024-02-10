package com.synrgy.domain.response.airport


import com.google.gson.annotations.SerializedName

data class Arrival(
    @SerializedName("airport")
    val airport: Airport?,
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