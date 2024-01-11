package com.synrgy.aeroswift.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.synrgy.aeroswift.databinding.ItemTicketBinding

class TicketPromoAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
            val binding = ItemTicketBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
            return object : RecyclerView.ViewHolder(binding.root) {}
        }

        override fun getItemCount(): Int {
            return 3
        }

        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        }
}