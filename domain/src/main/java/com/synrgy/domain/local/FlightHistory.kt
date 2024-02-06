package com.synrgy.domain.local

data class FlightHistory(
    val id: Int,
    var category: String,
    var total: Long? = null,
    var time: Long? = null
)
