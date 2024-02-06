package com.synrgy.aeroswift.presentation.viewholder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.synrgy.aeroswift.databinding.ItemPriceDetailsBinding
import com.synrgy.domain.local.PriceDetails
import com.synrgy.presentation.helper.Helper

class PriceDetailsViewHolder(
    private val binding: ItemPriceDetailsBinding
): RecyclerView.ViewHolder(binding.root) {
    fun bindData(data: PriceDetails) {
        binding.tvOrigin.text = data.oCity
        binding.tvDest.text = data.dCity

        val isChildVisible = data.childSeat != 0 && data.childPrice != null
        val isAdultVisible = data.adultSeat != 0 && data.adultPrice != null
        val isInfantVisible = data.infantSeat != 0 && data.infantPrice != null

        binding.trChild.visibility = if (isChildVisible) View.VISIBLE else View.GONE
        binding.trAdult.visibility = if (isAdultVisible) View.VISIBLE else View.GONE
        binding.trInfant.visibility = if (isInfantVisible) View.VISIBLE else View.GONE

        if (isChildVisible) {
            binding.tvTitleChild.text = "Child (${data.childSeat})"
            binding.tvPriceChild.text = "IDR ${Helper.formatPrice(data.childPrice!!)}"
        }
        if (isAdultVisible) {
            binding.tvTitleAdult.text = "Adult (${data.adultSeat})"
            binding.tvPriceAdult.text = "IDR ${Helper.formatPrice(data.adultPrice!!)}"
        }
        if (isInfantVisible) {
            binding.tvTitleInfant.text = "Infant (${data.infantSeat})"
            binding.tvPriceInfant.text = "IDR ${Helper.formatPrice(data.infantPrice!!)}"
        }
    }
}