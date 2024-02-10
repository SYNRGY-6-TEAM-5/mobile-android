package com.synrgy.domain.local

data class NotificationData(
    val id: String,
    val icon: Int,
    val title: String,
    val time: String,
    val isActive: Boolean = true
)
