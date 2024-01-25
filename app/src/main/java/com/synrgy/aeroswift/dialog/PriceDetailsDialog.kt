package com.synrgy.aeroswift.dialog

import android.app.Activity
import android.view.View
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.synrgy.aeroswift.databinding.DialogPriceDetailsBinding

class PriceDetailsDialog(
    private val activity: Activity
) {
    private lateinit var dialog: BottomSheetDialog
    private lateinit var binding: DialogPriceDetailsBinding

    fun show(
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

        if (!isProtection && !isInsurance && !isDelay) {
            binding.tableSeparatorAddon.visibility = View.GONE
            binding.tvAddon.visibility = View.GONE
            binding.tableAddon.visibility = View.GONE
        }

        binding.trFullProtection.visibility = if (isProtection) View.VISIBLE else View.GONE
        binding.trInsurance.visibility = if (isInsurance) View.VISIBLE else View.GONE
        binding.trFlightDelay.visibility = if (isDelay) View.VISIBLE else View.GONE

        binding.ivClose.setOnClickListener { dialog.dismiss() }
    }
}