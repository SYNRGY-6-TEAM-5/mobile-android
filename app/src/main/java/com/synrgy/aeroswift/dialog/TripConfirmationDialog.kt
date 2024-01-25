package com.synrgy.aeroswift.dialog

import android.app.Activity
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.synrgy.aeroswift.databinding.DialogTripConfirmationBinding

class TripConfirmationDialog(
    private val activity: Activity
) {
    private lateinit var dialog: BottomSheetDialog
    private lateinit var binding: DialogTripConfirmationBinding

    fun show() {
        dialog = BottomSheetDialog(activity)
        binding = DialogTripConfirmationBinding.inflate(activity.layoutInflater)
        val view = binding.root

        dialog.setContentView(view)
        dialog.setCancelable(true)
        dialog.show()

        binding.ivClose.setOnClickListener { dialog.dismiss() }
    }
}