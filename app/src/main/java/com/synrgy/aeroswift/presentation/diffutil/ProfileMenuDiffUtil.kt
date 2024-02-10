package com.synrgy.aeroswift.presentation.diffutil

import androidx.recyclerview.widget.DiffUtil
import com.synrgy.domain.local.ProfileMenu

class ProfileMenuDiffUtil: DiffUtil.ItemCallback<ProfileMenu>() {
    override fun areItemsTheSame(oldItem: ProfileMenu, newItem: ProfileMenu): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: ProfileMenu, newItem: ProfileMenu): Boolean {
        return oldItem.position == newItem.position
    }
}