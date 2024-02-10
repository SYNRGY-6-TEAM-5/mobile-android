package com.synrgy.aeroswift.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.synrgy.aeroswift.databinding.ItemProfileMenuBinding
import com.synrgy.aeroswift.presentation.diffutil.ProfileMenuDiffUtil
import com.synrgy.aeroswift.presentation.viewholder.ProfileMenuViewHolder
import com.synrgy.domain.local.ProfileMenu

class ProfileMenuAdapter(
    private val clickListener: (ProfileMenu) -> Unit
): ListAdapter<ProfileMenu, ProfileMenuViewHolder>(
    ProfileMenuDiffUtil()
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProfileMenuViewHolder {
        return ProfileMenuViewHolder(
            ItemProfileMenuBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: ProfileMenuViewHolder, position: Int) {
        holder.bindData(getItem(position))

        holder.itemView.setOnClickListener { clickListener(getItem(position)) }
    }
}