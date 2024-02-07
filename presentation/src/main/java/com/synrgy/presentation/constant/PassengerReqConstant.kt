package com.synrgy.presentation.constant

import com.synrgy.domain.local.PassengerReq
import com.synrgy.presentation.R

object PassengerReqConstant {
    fun getData(): ArrayList<PassengerReq> {
        return arrayListOf(
            PassengerReq(
                id = 1,
                image = R.drawable.img_pregnant,
                text = "Pregnant passengers must check in directly at the airport."
            ),
            PassengerReq(
                id = 2,
                image = R.drawable.img_special_needs,
                text = "Passengers with special needs (such as disabled passengers, Unaccompanied Minors) must check-in directly at the airport."
            ),
            PassengerReq(
                id = 3,
                image = R.drawable.img_infant,
                text = "Infant passengers can check-in online, but must still report to the airport check-in counter before boarding the plane"
            )
        )
    }
}