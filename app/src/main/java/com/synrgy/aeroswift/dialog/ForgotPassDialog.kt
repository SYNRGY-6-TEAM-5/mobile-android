package com.synrgy.aeroswift.dialog

import android.app.Activity
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.button.MaterialButton
import com.synrgy.aeroswift.R
import com.synrgy.aeroswift.presentation.ForgotPasswordActivity
import com.synrgy.aeroswift.presentation.LoginActivity
import com.synrgy.aeroswift.presentation.viewmodel.MainViewModel

class ForgotPassDialog(
    private val activity: Activity,
) {
    private lateinit var dialog: BottomSheetDialog

    fun show() {
        dialog = BottomSheetDialog(activity)
        val inflater = activity.layoutInflater
        val view = inflater.inflate(R.layout.dialog_pass_recovery, null)

        dialog.setContentView(view)
        dialog.setCancelable(true)
        dialog.show()

        val btnNext = view?.findViewById<MaterialButton>(R.id.btn_next_pass_recovery)

        btnNext?.setOnClickListener {
            ForgotPasswordActivity.startActivity(activity)
            dialog.dismiss()
        }
    }
}