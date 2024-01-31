package com.synrgy.aeroswift.dialog

import android.app.Activity
import android.app.AlertDialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.InsetDrawable
import com.synrgy.aeroswift.databinding.DialogAuthBinding
import com.synrgy.aeroswift.presentation.AuthActivity

class AuthDialog(
    private val activity: Activity
) {
    private lateinit var dialog: AlertDialog
    private lateinit var binding: DialogAuthBinding

    fun show() {
        val builder = AlertDialog.Builder(activity)
        binding = DialogAuthBinding.inflate(activity.layoutInflater)
        val view = binding.root

        builder.setView(view)
        builder.setCancelable(false)

        val back = ColorDrawable(Color.TRANSPARENT)
        val margin = 16
        val inset = InsetDrawable(back, margin)

        dialog = builder.create()
        dialog.window?.setBackgroundDrawable(inset)
        dialog.show()

        binding.btnSignIn.setOnClickListener {
            AuthActivity.startActivity(activity)
            activity.finish()
        }
    }

    fun dismiss() {
        dialog.dismiss()
    }
}