package com.synrgy.data.local.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.synrgy.domain.local.FlightSearch

@Entity(tableName = "flight_searches")
data class FlightSearchEntity(
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: String,

    @ColumnInfo(name = "origin")
    val origin: String?,

    @ColumnInfo(name = "destination")
    val destination: String?,

    @ColumnInfo(name = "oCity")
    val oCity: String?,

    @ColumnInfo(name = "dCity")
    val dCity: String?,

    @ColumnInfo(name = "depDate")
    val depDate: String?,

    @ColumnInfo(name = "retDate")
    val retDate: String?,

    @ColumnInfo(name = "tripType")
    val tripType: String?,

    @ColumnInfo(name = "ticketClass")
    val ticketClass: String?,

    @ColumnInfo(name = "adultSeat")
    val adultSeat: Int?,

    @ColumnInfo(name = "childSeat")
    val childSeat: Int?,

    @ColumnInfo(name = "totalSeat")
    val totalSeat: Int?,

    @ColumnInfo(name = "infantSeat")
    val infantSeat: Int?
)

fun FlightSearch.toEntity(): FlightSearchEntity {
    return FlightSearchEntity(id!!, origin, destination, oCity, dCity, depDate, retDate, tripType, ticketClass, adultSeat, childSeat, totalSeat, infantSeat)
}

fun FlightSearchEntity.toFlightSearch(): FlightSearch {
    return FlightSearch(id, origin, destination, oCity, dCity, depDate, retDate, tripType, ticketClass, adultSeat, childSeat, totalSeat, infantSeat)
}