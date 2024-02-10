package com.synrgy.data.local.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.synrgy.domain.local.RecentAirport
import com.synrgy.domain.response.airport.AirportData

@Entity(tableName = "recent_airports")
data class RecentAirportEntity(
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: Int,

    @ColumnInfo(name = "userId")
    val userId: String,

    @ColumnInfo(name = "airPortName")
    val airPortName: String?,

    @ColumnInfo(name = "cityIataCode")
    val cityIataCode: String?,

    @ColumnInfo(name = "cityName")
    val cityName: String?,

    @ColumnInfo(name = "countryIsoCode")
    val countryIsoCode: String?,

    @ColumnInfo(name = "countryName")
    val countryName: String?,

    @ColumnInfo(name = "gmt")
    val gmt: String?,

    @ColumnInfo(name = "iataCode")
    val iataCode: String?
)

fun List<RecentAirportEntity>.toRecentAirport(): List<RecentAirport> {
    return this.map {
        RecentAirport(
            it.id,
            it.airPortName,
            it.cityIataCode,
            it.cityName,
            it.countryIsoCode,
            it.countryName,
            it.gmt,
            it.iataCode
        )
    }
}

fun List<RecentAirportEntity>.toAirportData(): List<AirportData> {
    return this.map {
        AirportData(
            it.airPortName,
            it.cityIataCode,
            it.cityName,
            it.countryIsoCode,
            it.countryName,
            null,
            it.gmt,
            it.iataCode,
            it.id,
            null
        )
    }
}

fun List<RecentAirport>.toRecentAirportEntity(userId: String): List<RecentAirportEntity> {
    return this.map {
        RecentAirportEntity(
            it.id,
            userId,
            it.airPortName,
            it.cityIataCode,
            it.cityName,
            it.countryIsoCode,
            it.countryName,
            it.gmt,
            it.iataCode
        )
    }
}

fun RecentAirport.toRecentAirportEntity(userId: String): RecentAirportEntity {
    return RecentAirportEntity(id, userId, airPortName, cityIataCode, cityName, countryIsoCode, countryName, gmt, iataCode)
}

fun AirportData.toRecentAirportEntity(userId: String): RecentAirportEntity {
    return RecentAirportEntity(id, userId, airPortName, cityIataCode, cityName, countryIsoCode, countryName, gmt, iataCode)
}