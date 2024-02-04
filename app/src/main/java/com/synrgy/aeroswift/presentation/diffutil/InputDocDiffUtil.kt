package com.synrgy.aeroswift.presentation.diffutil

import androidx.recyclerview.widget.DiffUtil
import com.synrgy.domain.local.DocumentData

class InputDocDiffUtil: DiffUtil.ItemCallback<DocumentData>() {
    override fun areItemsTheSame(oldItem: DocumentData, newItem: DocumentData): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: DocumentData, newItem: DocumentData): Boolean {
        return oldItem.id == newItem.id
    }
}