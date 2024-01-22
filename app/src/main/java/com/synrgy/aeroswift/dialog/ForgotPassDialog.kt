package com.synrgy.aeroswift.dialog

import android.app.Activity
import android.view.View
import android.view.WindowManager
import androidx.lifecycle.LifecycleOwner
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.synrgy.aeroswift.databinding.DialogPassRecoveryBinding
import com.synrgy.aeroswift.presentation.viewmodel.forgotpassword.ForgotPasswordViewModel
import com.synrgy.domain.body.forgotpassword.ForgotPasswordBody
import com.synrgy.domain.response.error.ErrorItem


class ForgotPassDialog(
    private val activity: Activity,
    private val viewModel: ForgotPasswordViewModel,
    private val viewLifecycleOwner: LifecycleOwner
) {
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

        observeViewModel()

        binding.btnNextPassRecovery.setOnClickListener {
            val email = binding.passwordTiEmail.text.toString()
            viewModel.forgotPassword(ForgotPasswordBody(email))
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

    private fun observeViewModel() {
        viewModel.error.observe(viewLifecycleOwner, ::handleError)
        viewModel.errors.observe(viewLifecycleOwner, ::handleErrors)
    }

    private fun handleError(error: String) {
        if (error.isNotBlank() && error.isNotEmpty()) {
            binding.passwordTilEmail.error = error
        }
    }

    private fun handleErrors(errors: List<ErrorItem?>?) {
        if (!errors.isNullOrEmpty()) {
            var emailMessage = ""

            for (error in errors) {
                when (error?.field) {
                    "email" -> emailMessage += error.defaultMessage + "\n"
                }
            }

            binding.passwordTilEmail.error = emailMessage.replace(",", "\n")
        }
    }
}