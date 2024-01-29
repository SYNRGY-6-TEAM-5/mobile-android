package com.synrgy.presentation.constant

import com.synrgy.domain.FaqData
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
                description = "You can order easily using our online platform. When you find a flight you need, you can add it to cart, login and go through the ordering process. After the order is ready, you will receive order summary to your email. Order summary will also be stored to your account.",
                icon = R.drawable.ic_arrow_down
            ),
            FaqData(
                number = 3,
                title = "What if my flight delayed?",
                description = "You can order easily using our online platform. When you find a flight you need, you can add it to cart, login and go through the ordering process. After the order is ready, you will receive order summary to your email. Order summary will also be stored to your account.",
                icon = R.drawable.ic_arrow_down
            ),
            FaqData(
                number = 4,
                title = "What payment methods can I use?",
                description = "You can order easily using our online platform. When you find a flight you need, you can add it to cart, login and go through the ordering process. After the order is ready, you will receive order summary to your email. Order summary will also be stored to your account.",
                icon = R.drawable.ic_arrow_down
            ),
            FaqData(
                number = 5,
                title = "What information should I input when ordering?",
                description = "You can order easily using our online platform. When you find a flight you need, you can add it to cart, login and go through the ordering process. After the order is ready, you will receive order summary to your email. Order summary will also be stored to your account.",
                icon = R.drawable.ic_arrow_down
            )
        )
    }
}