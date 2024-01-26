package com.synrgy.presentation.constant

import com.synrgy.domain.ProfileMenu
import com.synrgy.presentation.R

object ProfileMenuConstant {
    fun getData(): ArrayList<ProfileMenu> {
        return arrayListOf(
            ProfileMenu(
                icon = R.drawable.ic_airplane,
                title = "Flight Order",
                position = 1
            ),
            ProfileMenu(
                icon = R.drawable.ic_people,
                title = "Save Passenger Data",
                position = 2
            ),
            ProfileMenu(
                icon = R.drawable.ic_notification_bing,
                title = "Notification Setting",
                position = 3
            ),
            ProfileMenu(
                icon = R.drawable.ic_location,
                title = "Location Setting",
                position = 4
            ),
            ProfileMenu(
                icon = R.drawable.ic_message_question,
                title = "Frequently Ask Question (FAQ)",
                position = 5
            ),
        )
    }
}