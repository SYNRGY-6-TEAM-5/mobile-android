package com.synrgy.presentation.constant

import com.synrgy.domain.local.FlightHistory
import java.util.Calendar

object FlightHistoryConstant {
    fun getData(): ArrayList<FlightHistory> {
        val data = arrayListOf<FlightHistory>()

        val currentTime = Calendar.getInstance()
        currentTime.add(Calendar.HOUR_OF_DAY, 1)
        val timestamp = currentTime.timeInMillis

        for (i in 1..3) {
            data.add(FlightHistory(i, Constant.FlightHistoryCategory.AWAITING_PAYMENT.value, 2230900, timestamp))
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