package com.synrgy.presentation.constant

import com.synrgy.domain.local.NotificationData
import com.synrgy.presentation.R

object NotificationConstant {
    fun getData(): ArrayList<NotificationData> {
        return arrayListOf(
            NotificationData(
                id = "1",
                icon = R.drawable.img_notif_discount,
                title = "Enjoy new user promos now, get discounts up to 50%",
                time = "1 min ago"
            ),
            NotificationData(
                id = "2",
                icon = R.drawable.img_notif_discount,
                title = "Enjoy new user promos now, get discounts up to 50%",
                time = "1 min ago"
            ),
            NotificationData(
                id = "3",
                icon = R.drawable.img_notif_discount,
                title = "Enjoy new user promos now, get discounts up to 50%",
                time = "1 min ago"
            ),
            NotificationData(
                id = "4",
                icon = R.drawable.img_notif_flight,
                title = "Your flight is delayed due to bad weather",
                time = "1 min ago"
            ),
            NotificationData(
                id = "5",
                icon = R.drawable.img_notif_flight,
                title = "Your flight is delayed due to bad weather",
                time = "1 min ago"
            ),
            NotificationData(
                id = "6",
                icon = R.drawable.img_notif_flight,
                title = "Your flight is delayed due to bad weather",
                time = "1 min ago"
            ),
            NotificationData(
                id = "7",
                icon = R.drawable.img_notif_transaction,
                title = "Congratulations, your transaction was successful!",
                time = "1 min ago"
            ),
            NotificationData(
                id = "8",
                icon = R.drawable.img_notif_transaction,
                title = "Congratulations, your transaction was successful!",
                time = "1 min ago"
            ),
            NotificationData(
                id = "9",
                icon = R.drawable.img_notif_transaction,
                title = "Congratulations, your transaction was successful!",
                time = "1 min ago"
            ),
        )
    }
}