package com.synrgy.presentation.constant

object Constant {
    const val MIME_TYPE_IMAGE = "image/*"

    enum class FlightHistoryCategory(val value: String) {
        AWAITING_PAYMENT("Awaiting Payment"),
        PROCESSING("Processing"),
        COMPLETED("Completed"),
        CANCELED("Canceled")
    }

    enum class TripType(val value: String) {
        ONE_WAY("one-way"),
        ROUNDTRIP("roundtrip")
    }

    enum class AddonType(val value: String) {
        BAGGAGE("baggage"),
        MEALS("meals")
    }

    enum class PassengerType(val value: String) {
        ADULT("adult"),
        CHILD("child"),
        INFANT("infant")
    }
}