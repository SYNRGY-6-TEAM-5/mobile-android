package com.synrgy.aeroswift.presentation.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.synrgy.aeroswift.databinding.ItemNotificationBinding
import com.synrgy.domain.NotificationData

class NotificationViewHolder(
    private val binding: ItemNotificationBinding
): RecyclerView.ViewHolder(binding.root) {
    fun bindData(data: NotificationData) {
        binding.ivNotification.setImageResource(data.icon)
        binding.tvTitle.text = data.title
        binding.tvTime.text = data.time
    }
}