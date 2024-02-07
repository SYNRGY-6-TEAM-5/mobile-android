package com.synrgy.presentation.constant

import com.synrgy.domain.local.BulletList

object ImportantInformationConstant {
    fun getData(): ArrayList<BulletList> {
        return arrayListOf(
            BulletList("To board the plane, you must print your boarding pass, or pick it up at the airport check-in counter."),
            BulletList("Check-in counters will close 30 minutes before your departure time."),
            BulletList("Passengers must check in their baggage at the check-in counter or baggage handling counter at the airport within the specified time limit."),
            BulletList("Refunds and rescheduling cannot be done after check-in.")
        )
    }
}