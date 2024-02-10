package com.synrgy.presentation.constant

import com.synrgy.domain.local.BulletList

object CheckInSuccessConstant {
    fun getData(): ArrayList<BulletList> {
        return arrayListOf(
            BulletList("View your boarding pass via your e-ticket and make sure all the information is correct."),
            BulletList("Collect your boarding pass at the inside airport check-in counter or kiosk specified time limit."),
            BulletList("Pregnant passengers are asked to submit health files addition to the airport check-in counter."),
            BulletList("Your departure gate may experience changes. Double check the flight information you upon arrival at the airport.")
        )
    }
}