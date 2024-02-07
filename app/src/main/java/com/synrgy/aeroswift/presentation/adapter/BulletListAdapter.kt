package com.synrgy.aeroswift.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.synrgy.aeroswift.databinding.ItemBulletListBinding
import com.synrgy.aeroswift.presentation.diffutil.BulletListDiffUtil
import com.synrgy.aeroswift.presentation.viewholder.BulletListViewHolder
import com.synrgy.domain.local.BulletList

class BulletListAdapter: ListAdapter<BulletList, BulletListViewHolder>(
    BulletListDiffUtil()
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BulletListViewHolder {
        return BulletListViewHolder(
            ItemBulletListBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: BulletListViewHolder, position: Int) {
        holder.bindData(getItem(position))
    }
}