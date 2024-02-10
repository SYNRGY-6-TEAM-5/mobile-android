package com.synrgy.aeroswift.presentation.viewholder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.textview.MaterialTextView
import com.synrgy.aeroswift.R
import com.synrgy.aeroswift.databinding.ItemPaymentInstructionBinding
import com.synrgy.domain.local.PaymentInstruction

class PaymentInstructionViewHolder(
    private val binding: ItemPaymentInstructionBinding
): RecyclerView.ViewHolder(binding.root) {
    private val layoutView = binding.layoutPaymentInstruction

    val textView = binding.tvDescription

    fun bindData(
        data: PaymentInstruction,
        textViewList: ArrayList<MaterialTextView>
    ) {
        binding.tvTitle.text = data.title
        binding.tvDescription.text = data.description
        layoutView.setOnClickListener {
            val isTextHidden = binding.tvDescription.visibility == View.GONE

            if (isTextHidden) {
                textViewList.forEach { it.visibility = View.GONE }
            }

            val visibility = if (isTextHidden) View.VISIBLE else View.GONE
            val icon = if (isTextHidden) R.drawable.ic_arrow_up_2 else R.drawable.ic_arrow_down

            binding.tvDescription.visibility = visibility
            binding.ivIcon.setImageResource(icon)
        }
    }
}