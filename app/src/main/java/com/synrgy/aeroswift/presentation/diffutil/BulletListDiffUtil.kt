package com.synrgy.aeroswift.presentation.diffutil

import androidx.recyclerview.widget.DiffUtil
import com.synrgy.domain.local.BulletList

class BulletListDiffUtil: DiffUtil.ItemCallback<BulletList>() {
    override fun areItemsTheSame(oldItem: BulletList, newItem: BulletList): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: BulletList, newItem: BulletList): Boolean {
        return oldItem.text == newItem.text
    }
}