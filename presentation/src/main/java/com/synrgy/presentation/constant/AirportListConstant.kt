package com.synrgy.presentation.constant

import com.synrgy.domain.AirportList

object AirportListConstant {

    fun getAirportList(): ArrayList<AirportList> {
        return arrayListOf(
            AirportList(
                title = "Yogyakarta (JOG), ID",
                subtitle = "Yogyakarta International Airport (YIA)",
                category = "YIA"
            ),
            AirportList(
                title = "Jakarta (JKT), ID",
                subtitle = "Soekarno Hatta International Airport (CGK)",
                category = "CGK"
            ),
            AirportList(
                title = "Samarinda (AAP), ID",
                subtitle = "APT Pranoto International Airport (AAP)",
                category = "AAP"
            ),
            AirportList(
                title = "All Ain (AAN), UAE",
                subtitle = "Al Ain International Airport (AAN)",
                category = "AAN"
            ),
            AirportList(
                title = "Abidjan (ABJ), Ivorie",
                subtitle = "Houphouet Boigny Airport (ABJ)",
                category = "CGK"
            ),
            AirportList(
                title = "Abuja (ABV), Nigeria",
                subtitle = "Nnamdi Azikiwe Intl Airport (ABV)",
                category = "ABV"
            ),
        )
    }
}