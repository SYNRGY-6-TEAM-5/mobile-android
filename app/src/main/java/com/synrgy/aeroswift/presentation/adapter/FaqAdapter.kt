package com.synrgy.aeroswift.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.google.android.material.textview.MaterialTextView
import com.synrgy.aeroswift.databinding.ItemFaqCardBinding
import com.synrgy.aeroswift.presentation.diffutil.FaqDiffUtil
import com.synrgy.aeroswift.presentation.viewholder.FaqViewHolder
import com.synrgy.domain.local.FaqData

class FaqAdapter: ListAdapter<FaqData, FaqViewHolder>(
    FaqDiffUtil()
) {
    private val textViewList = ArrayList<MaterialTextView>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FaqViewHolder {
        return FaqViewHolder(
            ItemFaqCardBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: FaqViewHolder, position: Int) {
        if (!textViewList.contains(holder.textView)) {
            textViewList.add(holder.textView)
        }

        holder.bindData(getItem(position), textViewList)
    }
}