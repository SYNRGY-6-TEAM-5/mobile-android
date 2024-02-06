package com.synrgy.domain.local

data class FlightSearch(
    var id: String? = null,
    var origin: String? = null,
    var destination: String? = null,
    var oCity: String? = null,
    var dCity: String? = null,
    var depDate: String? = null,
    var retDate: String? = null,
    var tripType: String? = null,
    var ticketClass: String? = null,
    var adultSeat: Int? = null,
    var childSeat: Int? = null,
    var totalSeat: Int? = null,
    var infantSeat: Int? = null
)
