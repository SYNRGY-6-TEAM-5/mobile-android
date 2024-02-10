package com.synrgy.presentation.constant

import com.synrgy.domain.local.FaqData
import com.synrgy.presentation.R

object FaqConstant {
    fun getData(): ArrayList<FaqData> {
        return arrayListOf(
            FaqData(
                number = 1,
                title = "How can I order?",
                description = "You can order easily using our online platform. When you find a flight you need, you can add it to cart, login and go through the ordering process. After the order is ready, you will receive order summary to your email. Order summary will also be stored to your account.",
                icon = R.drawable.ic_arrow_down
            ),
            FaqData(
                number = 2,
                title = "Can I cancel my order?",
                description = "We understand that plans can change. You may cancel your order within a specified timeframe. Please refer to our cancellation policy for details on the cancellation process, associated fees, and the applicable time window.",
                icon = R.drawable.ic_arrow_down
            ),
            FaqData(
                number = 3,
                title = "What if my flight delayed?",
                description = "In the event of a flight delay, we recommend contacting the airline directly for real-time updates. Additionally, check the terms and conditions of your booking for information on compensation or assistance in case of delays. If you face difficulties, our customer support team is here to assist you.",
                icon = R.drawable.ic_arrow_down
            ),
            FaqData(
                number = 4,
                title = "What payment methods can I use?",
                description = "We accept various payment methods to provide convenience for our customers. You can typically use credit/debit cards, bank transfers, or other secure online payment options. The available payment methods will be displayed during the checkout process.",
                icon = R.drawable.ic_arrow_down
            ),
            FaqData(
                number = 5,
                title = "What information should I input when ordering?",
                description = "When placing an order, ensure you provide accurate and complete information. This typically includes your personal details, contact information, payment details, and any other relevant information required for the booking. Review your input before confirming the order to avoid any errors or issues.",
                icon = R.drawable.ic_arrow_down
            )
        )
    }
}