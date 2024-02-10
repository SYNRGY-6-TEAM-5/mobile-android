package com.synrgy.aeroswift.dialog

import android.app.Activity
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.synrgy.aeroswift.databinding.DialogPriceDetailsBinding
import com.synrgy.aeroswift.presentation.adapter.PriceDetailsAdapter
import com.synrgy.domain.local.PriceDetails

class PriceDetailsDialog(
    private val activity: Activity
) {
    private lateinit var dialog: BottomSheetDialog
    private lateinit var binding: DialogPriceDetailsBinding
    private val adapter = PriceDetailsAdapter()

    fun show(
        priceList: ArrayList<PriceDetails>,
        isProtection: Boolean = false,
        isInsurance: Boolean = false,
        isDelay: Boolean = false
    ) {
        dialog = BottomSheetDialog(activity)
        binding = DialogPriceDetailsBinding.inflate(activity.layoutInflater)
        val view = binding.root

        dialog.setContentView(view)
        dialog.setCancelable(true)
        dialog.show()

        binding.rvPriceDetails.layoutManager = LinearLayoutManager(activity)
        binding.rvPriceDetails.adapter = this.adapter
        this.adapter.submitList(priceList)

        if (!isProtection && !isInsurance && !isDelay) {
            binding.tvAddon.visibility = View.GONE
            binding.tableAddon.visibility = View.GONE
        }

        binding.trFullProtection.visibility = if (isProtection) View.VISIBLE else View.GONE
        binding.trInsurance.visibility = if (isInsurance) View.VISIBLE else View.GONE
        binding.trFlightDelay.visibility = if (isDelay) View.VISIBLE else View.GONE

        binding.ivClose.setOnClickListener { dialog.dismiss() }
    }
}