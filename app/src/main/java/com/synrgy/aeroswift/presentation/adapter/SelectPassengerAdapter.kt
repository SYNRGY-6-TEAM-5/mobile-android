package com.synrgy.aeroswift.presentation.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.synrgy.aeroswift.databinding.ItemSelectPassengerBinding
import com.synrgy.aeroswift.models.RvPassengerModels

class SelectPassengerAdapter(private val passengers: MutableList<RvPassengerModels>) :
    RecyclerView.Adapter<SelectPassengerAdapter.PassengerViewHolder>() {
    class PassengerViewHolder(private val binding: ItemSelectPassengerBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val passengerType = binding.tvPassenger
        val passengerAges = binding.tvAge
        val btnMinus = binding.btnMinus
        val btnPlus = binding.btnPlus
        val tvCount = binding.tvCount
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PassengerViewHolder {
        val binding = ItemSelectPassengerBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return PassengerViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return passengers.size
    }

    override fun onBindViewHolder(holder: PassengerViewHolder, position: Int) {
        holder.passengerType.text = passengers[position].type
        holder.passengerAges.text = passengers[position].ages
        holder.tvCount.text = passengers[position].count.toString()
        var sumCount = 0

        holder.btnMinus.setOnClickListener {
            if (passengers[position].count > 0) {
                passengers[position].count--
                holder.tvCount.text = passengers[position].count.toString()
            }
            sumCount = passengers.sumOf { it.count }
            Log.d("sumCount", sumCount.toString())
        }

        holder.btnPlus.setOnClickListener {
            passengers[position].count++
            holder.tvCount.text = passengers[position].count.toString()
            sumCount = passengers.sumOf { it.count }
            Log.d("sumCount", sumCount.toString())
        }


    }
}