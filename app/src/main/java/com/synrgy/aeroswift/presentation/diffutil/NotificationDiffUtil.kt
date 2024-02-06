package com.synrgy.aeroswift.presentation.diffutil

import androidx.recyclerview.widget.DiffUtil
import com.synrgy.domain.local.NotificationData

class NotificationDiffUtil: DiffUtil.ItemCallback<NotificationData>() {
    override fun areItemsTheSame(oldItem: NotificationData, newItem: NotificationData): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: NotificationData, newItem: NotificationData): Boolean {
        return oldItem.id == newItem.id
    }
}