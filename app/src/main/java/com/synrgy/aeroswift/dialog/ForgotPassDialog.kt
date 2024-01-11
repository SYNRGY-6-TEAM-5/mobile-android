package com.synrgy.aeroswift.dialog

import android.app.Activity
import android.view.View
import android.view.WindowManager
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.button.MaterialButton
import com.synrgy.aeroswift.R
import com.synrgy.aeroswift.presentation.ForgotPasswordActivity

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

        val parentLayout =
            dialog.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet)

        parentLayout?.let { it ->
            val behaviour = BottomSheetBehavior.from(it)
            setupFullHeight(it)
            behaviour.state = BottomSheetBehavior.STATE_EXPANDED
        }

        val btnNext = view?.findViewById<MaterialButton>(R.id.btn_next_pass_recovery)

        btnNext?.setOnClickListener {
            ForgotPasswordActivity.startActivity(activity)
            dialog.dismiss()
        }
    }

    private fun setupFullHeight(bottomSheet: View) {
        val layoutParams = bottomSheet.layoutParams
        layoutParams.height = WindowManager.LayoutParams.MATCH_PARENT
        bottomSheet.layoutParams = layoutParams
    }
}