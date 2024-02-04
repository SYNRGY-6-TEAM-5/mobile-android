package com.synrgy.presentation.constant

import com.synrgy.domain.local.PassengerData

object PassengerConstant {
    fun getData(): ArrayList<PassengerData> {
        return arrayListOf(
            PassengerData(
                id = "jack harris",
                name = "Jack Harris",
                category = Constant.PassengerType.ADULT.value
            ),
            PassengerData(
                id = "gigi hadid",
                name = "Gigi Hadid",
                category = Constant.PassengerType.ADULT.value
            )
        )
    }
}