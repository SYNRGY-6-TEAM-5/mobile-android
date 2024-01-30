package com.synrgy.presentation.constant

import com.synrgy.domain.FlightOrder

object FlightOrderConstant {
    fun getData(): ArrayList<FlightOrder> {
        val awaiting = Constant.FlightHistoryCategory.AWAITING_PAYMENT.value
        val awaitingCount = FlightHistoryConstant.getData().filter {
            it.category == awaiting
        }.size

        val processing = Constant.FlightHistoryCategory.PROCESSING.value
        val processingCount = FlightHistoryConstant.getData().filter {
            it.category == processing
        }.size

        val completed = Constant.FlightHistoryCategory.COMPLETED.value
        val completedCount = FlightHistoryConstant.getData().filter {
            it.category == completed
        }.size

        val canceled = Constant.FlightHistoryCategory.CANCELED.value
        val canceledCount = FlightHistoryConstant.getData().filter {
            it.category == canceled
        }.size

        return arrayListOf(
            FlightOrder(
                title = awaiting,
                count = awaitingCount,
                position = 1
            ),
            FlightOrder(
                title = processing,
                count = processingCount,
                position = 2
            ),
            FlightOrder(
                title = completed,
                count = completedCount,
                position = 3
            ),
            FlightOrder(
                title = canceled,
                count = canceledCount,
                position = 4
            )
        )
    }
}