package com.synrgy.presentation.constant

import com.synrgy.domain.PassengerData

object PassengerConstant {
    fun getData(): ArrayList<PassengerData> {
        return arrayListOf(
            PassengerData(
                id = 1,
                name = "Jack Harris"
            ),
            PassengerData(
                id = 2,
                name = "Gigi Hadid"
            )
        )
    }
}