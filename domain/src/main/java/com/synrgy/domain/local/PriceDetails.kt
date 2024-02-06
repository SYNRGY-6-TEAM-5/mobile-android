package com.synrgy.domain.local

data class PriceDetails(
    val id: String,
    val oCity: String,
    val dCity: String,
    val adultSeat: Int,
    val childSeat: Int,
    val infantSeat: Int,
    val adultPrice: Long?,
    val childPrice: Long?,
    val infantPrice: Long?,
)
