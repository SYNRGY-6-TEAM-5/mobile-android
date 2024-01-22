package com.synrgy.aeroswift.dialog

import android.app.Activity
import android.app.AlertDialog
import android.graphics.drawable.ColorDrawable
import com.synrgy.aeroswift.databinding.DialogLoadingBinding

class LoadingDialog(
    private val activity: Activity
) {

    private lateinit var dialog: AlertDialog
    private lateinit var binding: DialogLoadingBinding

    fun startLoadingDialog() {
        val builder = AlertDialog.Builder(activity)
        binding = DialogLoadingBinding.inflate(activity.layoutInflater)
        val view = binding.root

        builder.setView(view)
        builder.setCancelable(false)

        dialog = builder.create()
        dialog.window?.setBackgroundDrawable(ColorDrawable(android.graphics.Color.TRANSPARENT))
        dialog.show()
    }

    fun dismissDialog() {
        dialog.dismiss()
    }
}