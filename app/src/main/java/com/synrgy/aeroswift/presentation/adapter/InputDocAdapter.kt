package com.synrgy.aeroswift.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.synrgy.aeroswift.databinding.ItemInputDocumentBinding
import com.synrgy.aeroswift.presentation.diffutil.InputDocDiffUtil
import com.synrgy.aeroswift.presentation.viewholder.InputDocViewHolder
import com.synrgy.domain.local.DocumentData

class InputDocAdapter: ListAdapter<DocumentData, InputDocViewHolder>(
    InputDocDiffUtil()
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InputDocViewHolder {
        return InputDocViewHolder(
            ItemInputDocumentBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: InputDocViewHolder, position: Int) {
        holder.bindData(getItem(position))
    }
}