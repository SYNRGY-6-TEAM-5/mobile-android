package com.synrgy.aeroswift.presentation.viewholder

import android.widget.RadioButton
import androidx.recyclerview.widget.RecyclerView
import com.synrgy.aeroswift.databinding.ItemPaymentMethodBinding
import com.synrgy.domain.local.PaymentMethod

class PaymentMethodViewHolder(
    private val binding: ItemPaymentMethodBinding,
): RecyclerView.ViewHolder(binding.root) {
    val radioButton = binding.rbPayment

    fun bindData(
        data: PaymentMethod,
        rbList: ArrayList<RadioButton>,
        clickListener: (PaymentMethod, isChecked: Boolean) -> Unit,
    ) {
        binding.ivBank.setImageResource(data.bankImage)
        binding.tvBank.text = data.bankName
        binding.rbPayment.setOnClickListener {
            clickListener(data, binding.rbPayment.isChecked)
            rbList.forEach { it.isChecked = false }
            binding.rbPayment.isChecked = true
        }
    }
}