package com.synrgy.aeroswift.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.google.android.material.textview.MaterialTextView
import com.synrgy.aeroswift.databinding.ItemPaymentInstructionBinding
import com.synrgy.aeroswift.presentation.diffutil.PaymentInstructionDiffUtil
import com.synrgy.aeroswift.presentation.viewholder.PaymentInstructionViewHolder
import com.synrgy.domain.local.PaymentInstruction

class PaymentInstructionAdapter: ListAdapter<PaymentInstruction, PaymentInstructionViewHolder>(
    PaymentInstructionDiffUtil()
) {
    private val textViewList = ArrayList<MaterialTextView>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PaymentInstructionViewHolder {
        return PaymentInstructionViewHolder(
            ItemPaymentInstructionBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: PaymentInstructionViewHolder, position: Int) {
        if (!textViewList.contains(holder.textView)) {
            textViewList.add(holder.textView)
        }

        holder.bindData(getItem(position), textViewList)
    }
}