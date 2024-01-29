package com.synrgy.aeroswift.presentation.diffutil

import androidx.recyclerview.widget.DiffUtil
import com.synrgy.domain.FaqData

class FaqDiffUtil: DiffUtil.ItemCallback<FaqData>() {
    override fun areItemsTheSame(oldItem: FaqData, newItem: FaqData): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: FaqData, newItem: FaqData): Boolean {
        return oldItem.number == newItem.number
    }
}