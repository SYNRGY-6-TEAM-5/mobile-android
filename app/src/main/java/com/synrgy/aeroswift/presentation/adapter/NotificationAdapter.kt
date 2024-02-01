package com.synrgy.aeroswift.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.synrgy.aeroswift.databinding.ItemNotificationBinding
import com.synrgy.aeroswift.presentation.diffutil.NotificationDiffUtil
import com.synrgy.aeroswift.presentation.viewholder.NotificationViewHolder
import com.synrgy.domain.local.NotificationData

class NotificationAdapter(
    private val clickListener: (NotificationData) -> Unit
): ListAdapter<NotificationData, NotificationViewHolder>(
    NotificationDiffUtil()
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotificationViewHolder {
        return NotificationViewHolder(
            ItemNotificationBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: NotificationViewHolder, position: Int) {
        holder.bindData(getItem(position))

        holder.itemView.setOnClickListener { clickListener(getItem(position)) }
    }
}