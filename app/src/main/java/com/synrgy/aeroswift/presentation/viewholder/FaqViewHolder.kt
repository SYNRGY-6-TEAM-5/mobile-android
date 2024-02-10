package com.synrgy.aeroswift.presentation.viewholder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.textview.MaterialTextView
import com.synrgy.aeroswift.R
import com.synrgy.aeroswift.databinding.ItemFaqCardBinding
import com.synrgy.domain.local.FaqData

class FaqViewHolder(
    private val binding: ItemFaqCardBinding
): RecyclerView.ViewHolder(binding.root) {
    private val cardView = binding.itemFaqCard

    val textView = binding.tvDescription

    fun bindData(
        data: FaqData,
        textViewList: ArrayList<MaterialTextView>,
    ) {
        binding.tvNumber.text = "${data.number}."
        binding.tvTitle.text = data.title
        binding.ivIcon.setImageResource(data.icon)
        binding.tvDescription.text = data.description

        cardView.setOnClickListener {
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