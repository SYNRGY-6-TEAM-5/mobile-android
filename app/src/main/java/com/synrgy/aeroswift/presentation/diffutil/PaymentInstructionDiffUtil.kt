package com.synrgy.aeroswift.presentation.diffutil

import androidx.recyclerview.widget.DiffUtil
import com.synrgy.domain.local.PaymentInstruction

class PaymentInstructionDiffUtil: DiffUtil.ItemCallback<PaymentInstruction>() {
    override fun areItemsTheSame(oldItem: PaymentInstruction, newItem: PaymentInstruction): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: PaymentInstruction, newItem: PaymentInstruction): Boolean {
        return oldItem.id == newItem.id
    }
}