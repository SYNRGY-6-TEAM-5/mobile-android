package com.synrgy.aeroswift.dialog

import android.app.Activity
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.synrgy.aeroswift.databinding.DialogPassRecoveryBinding
import com.synrgy.aeroswift.presentation.ForgotPasswordActivity
import com.synrgy.presentation.helper.Helper

class ForgotPassDialog(
    private val activity: Activity,
) {
    companion object {
        const val KEY_EMAIL_RECOVERY = "email_recovery"
    }

    private lateinit var dialog: BottomSheetDialog

    private lateinit var binding: DialogPassRecoveryBinding

    fun show() {
        dialog = BottomSheetDialog(activity)
        binding = DialogPassRecoveryBinding.inflate(activity.layoutInflater)
        val view = binding.root

        dialog.setContentView(view)
        dialog.setCancelable(true)
        dialog.show()

        val parentLayout =
            dialog.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet)

        parentLayout?.let {
            val behaviour = BottomSheetBehavior.from(it)
            setupFullHeight(it)
            behaviour.state = BottomSheetBehavior.STATE_EXPANDED
        }

        binding.btnNextPassRecovery.setOnClickListener {
            val email = binding.passwordTiEmail.text.toString().takeIf { it.isNotEmpty() }
                ?: "test@example.com"

            if (!Helper.isValidEmail(email)) {
                binding.passwordTilEmail.error = "Email is not valid"
            } else {
                val bundle = Bundle()
                bundle.putString(KEY_EMAIL_RECOVERY, email)
                ForgotPasswordActivity.startActivity(activity, bundle)
                dialog.dismiss()
            }
        }

        binding.toolbarPassRecovery.setOnClickListener {
            dialog.dismiss()
        }
    }

    private fun setupFullHeight(bottomSheet: View) {
        val layoutParams = bottomSheet.layoutParams
        layoutParams.height = WindowManager.LayoutParams.MATCH_PARENT
        bottomSheet.layoutParams = layoutParams
    }
}