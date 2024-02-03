package com.synrgy.aeroswift.presentation.viewholder

import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.google.android.material.card.MaterialCardView
import com.synrgy.aeroswift.R
import com.synrgy.aeroswift.databinding.ItemDepartBaggageBinding
import com.synrgy.domain.local.DepartBaggage
import com.synrgy.presentation.helper.Helper

class DepartBaggageViewHolder(
    private val binding: ItemDepartBaggageBinding
): ViewHolder(binding.root) {
    val cardView = binding.itemBaggageCard
    val item = binding

    fun bindData(
        data: DepartBaggage,
        cardViewList: ArrayList<MaterialCardView>,
        clickListener: (DepartBaggage) -> Unit,
    ) {
        val context = binding.root.context

        binding.baggageWeight.text = context.getString(R.string.depart_baggage_weight, data.weight.toString())
        binding.baggagePrice.text = context.getString(R.string.depart_baggage_price, Helper.formatPrice(data.price))

        cardView.setOnClickListener {
            clickListener(data)

            cardViewList.forEach {
                it.strokeColor = ContextCompat.getColor(context, R.color.gray_200)
                it.setCardBackgroundColor(ContextCompat.getColor(context, R.color.gray_50))
            }

            setActive(binding.itemBaggageCard)
        }
    }

    fun setActive(cardView: MaterialCardView) {
        val context = binding.root.context

        cardView.strokeColor = ContextCompat.getColor(context, R.color.error_500)
        cardView.setCardBackgroundColor(ContextCompat.getColor(context, R.color.error_50))
    }
}