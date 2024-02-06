package com.synrgy.presentation.constant

import com.synrgy.domain.local.FlightHistory

object FlightHistoryConstant {
    fun getData(): ArrayList<FlightHistory> {
        val data = arrayListOf<FlightHistory>()

        for (i in 1..3) {
            data.add(FlightHistory(i, Constant.FlightHistoryCategory.AWAITING_PAYMENT.value))
        }
        for (i in 4..5) {
            data.add(FlightHistory(i, Constant.FlightHistoryCategory.PROCESSING.value))
        }
        for (i in 6..9) {
            data.add(FlightHistory(i, Constant.FlightHistoryCategory.COMPLETED.value))
        }
        for (i in 10..15) {
            data.add(FlightHistory(i, Constant.FlightHistoryCategory.CANCELED.value))
        }

        return data
    }
}