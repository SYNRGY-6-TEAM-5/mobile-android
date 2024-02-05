package com.synrgy.aeroswift.presentation.adapter

import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.synrgy.aeroswift.databinding.ItemActiveOrderBinding


class ActiveOrderAdapter : RecyclerView.Adapter<ActiveOrderAdapter.ActiveOrderViewHolder>() {

    inner class ActiveOrderViewHolder(val binding: ItemActiveOrderBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ActiveOrderViewHolder {
        val binding = ItemActiveOrderBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ActiveOrderViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return 3
    }

    override fun onBindViewHolder(holder: ActiveOrderViewHolder, position: Int) {
        val timer = object : CountDownTimer(3600000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                val hour = millisUntilFinished / 3600000
                val minute = (millisUntilFinished % 3600000) / 60000
                val second = (millisUntilFinished % 60000) / 1000
                val time = "$hour:$minute:$second"
                holder.binding.btnCompletePayment.text = "Complete the Payment in $time"
            }

            override fun onFinish() {
                holder.binding.btnCompletePayment.text = "Complete the Payment"
            }
        }
        timer.start()

        holder.binding.btnCompletePayment.setOnClickListener {
            timer.cancel()
            holder.binding.btnCompletePayment.text = "Payment Completed"

        }

    }
}
