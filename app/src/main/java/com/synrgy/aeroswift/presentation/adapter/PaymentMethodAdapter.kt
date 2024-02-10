package com.synrgy.aeroswift.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.RadioButton
import androidx.recyclerview.widget.ListAdapter
import com.synrgy.aeroswift.databinding.ItemPaymentMethodBinding
import com.synrgy.aeroswift.presentation.diffutil.PaymentMethodDiffUtil
import com.synrgy.aeroswift.presentation.viewholder.PaymentMethodViewHolder
import com.synrgy.domain.local.PaymentMethod

class PaymentMethodAdapter(
    private val clickListener: (PaymentMethod) -> Unit
): ListAdapter<PaymentMethod, PaymentMethodViewHolder>(
    PaymentMethodDiffUtil()
) {
    private val rbList = arrayListOf<RadioButton>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PaymentMethodViewHolder {
        return PaymentMethodViewHolder(
            ItemPaymentMethodBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: PaymentMethodViewHolder, position: Int) {
        if (!rbList.contains(holder.radioButton)) {
            rbList.add(holder.radioButton)
        }

        holder.bindData(getItem(position), rbList, clickListener)
    }
}