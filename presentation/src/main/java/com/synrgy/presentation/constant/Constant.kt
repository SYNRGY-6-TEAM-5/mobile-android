package com.synrgy.presentation.constant

object Constant {
    const val MIME_TYPE_IMAGE = "image/*"

    enum class FlightHistoryCategory(val value: String) {
        AWAITING_PAYMENT("Awaiting Payment"),
        PROCESSING("Processing"),
        COMPLETED("Completed"),
        CANCELED("Canceled")
    }
}