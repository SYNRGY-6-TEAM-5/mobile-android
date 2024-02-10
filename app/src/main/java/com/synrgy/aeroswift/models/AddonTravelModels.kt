package com.synrgy.aeroswift.models

data class AddonTravelModels(
    val id: String,
    val trip: String,
    val price: Long,
    val origin: String,
    val destination: String,
    val name: String = "",
    val count: String = ""
)