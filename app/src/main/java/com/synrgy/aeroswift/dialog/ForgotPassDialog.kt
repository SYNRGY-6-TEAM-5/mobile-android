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

    private var emailMessage = ""

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
            emailMessage = ""
            val email = binding.passwordTiEmail.text.toString()
            viewModel.forgotPassword(ForgotPasswordBody(email))
        }

        binding.toolbarPassRecovery.setNavigationOnClickListener {
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
        viewModel.localError.observe(viewLifecycleOwner, ::handleLocalError)
    }

    private fun handleError(error: String) {
        if (error.isNotBlank() && error.isNotEmpty()) {
            binding.passwordTilEmail.error = error
        }
    }

    private fun handleErrors(errors: List<ErrorItem?>?) {
        if (!errors.isNullOrEmpty()) {
            for (error in errors) {
                when (error?.field) {
                    "email" -> emailMessage += error.defaultMessage + "\n"
                }
            }

            binding.passwordTilEmail.error = emailMessage.replace(",", "\n")
        }
    }

    private fun handleLocalError(error: Boolean) {
        if (error) {
            val email = binding.passwordTiEmail.text.toString()

            validateEmail(email)
            handleSetInputMessage()
        }
    }

    private fun validateEmail(email: String) {
        if (email.isEmpty() && email.isBlank()) {
            emailMessage += "Email is required.\n"
            emailMessage += "Email cannot be blank.\n"
        }
    }

    private fun handleSetInputMessage() {
        if (emailMessage.isNotEmpty() && emailMessage.isNotBlank()) {
            binding.passwordTilEmail.error = emailMessage.replace(",", "\n")
        }
    }
}