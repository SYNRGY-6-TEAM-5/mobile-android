package com.synrgy.aeroswift.dialog

import android.app.Activity
import android.view.View
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.synrgy.aeroswift.databinding.DialogConfirmationBinding

class ConfirmationDialog(
    private val activity: Activity,
    private val clickListener: () -> Unit
) {
    private lateinit var dialog: BottomSheetDialog
    private lateinit var binding: DialogConfirmationBinding

    fun show(
        heading: String,
        title: String,
        description: String?
    ) {
        dialog = BottomSheetDialog(activity)
        binding = DialogConfirmationBinding.inflate(activity.layoutInflater)
        val view = binding.root

        dialog.setContentView(view)
        dialog.setCancelable(true)
        dialog.show()

        binding.tvHeading.text = heading
        binding.tvTitle.text = title

        if (description.isNullOrBlank() || description.isEmpty()) {
            binding.tvDescription.visibility = View.GONE
        } else {
            binding.tvDescription.text = description
        }

        binding.ivClose.setOnClickListener { dialog.dismiss() }
        binding.btnContinue.setOnClickListener { clickListener() }
    }

    fun dismiss() {
        dialog.dismiss()
    }
}