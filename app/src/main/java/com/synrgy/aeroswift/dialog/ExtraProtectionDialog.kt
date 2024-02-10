package com.synrgy.aeroswift.dialog

import android.app.Activity
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.synrgy.aeroswift.databinding.DialogExtraProtectionBinding

class ExtraProtectionDialog(
    private val activity: Activity
) {
    private lateinit var dialog: BottomSheetDialog
    private lateinit var binding: DialogExtraProtectionBinding

    fun show(title: String, image: Int) {
        dialog = BottomSheetDialog(activity)
        binding = DialogExtraProtectionBinding.inflate(activity.layoutInflater)
        val view = binding.root

        dialog.setContentView(view)
        dialog.setCancelable(true)
        dialog.show()

        binding.tvTitle.text = title
        binding.ivProtection.setImageResource(image)

        binding.ivClose.setOnClickListener { dialog.dismiss() }
    }
}