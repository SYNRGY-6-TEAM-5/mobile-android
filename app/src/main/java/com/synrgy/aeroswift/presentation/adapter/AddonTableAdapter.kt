package com.synrgy.aeroswift.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.synrgy.aeroswift.databinding.ItemAddonTableBinding

class AddonTableAdapter: RecyclerView.Adapter<AddonTableAdapter.AddonTableViewHolder>() {

    inner class AddonTableViewHolder(val binding: ItemAddonTableBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AddonTableViewHolder {
        val binding = ItemAddonTableBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return AddonTableViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return 2
    }

    override fun onBindViewHolder(holder: AddonTableViewHolder, position: Int) {}
}