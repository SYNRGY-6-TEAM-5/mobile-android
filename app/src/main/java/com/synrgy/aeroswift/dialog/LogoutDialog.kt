package com.synrgy.aeroswift.dialog

import android.app.Activity
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.synrgy.aeroswift.databinding.DialogLogoutBinding
import com.synrgy.aeroswift.presentation.viewmodel.auth.AuthViewModel

class LogoutDialog(
    private val activity: Activity,
    private val viewModel: AuthViewModel
) {
    private lateinit var dialog: BottomSheetDialog
    private lateinit var binding: DialogLogoutBinding

    fun show() {
        dialog = BottomSheetDialog(activity)
        binding = DialogLogoutBinding.inflate(activity.layoutInflater)
        val view = binding.root

        dialog.setContentView(view)
        dialog.setCancelable(true)
        dialog.show()

        binding.ivClose.setOnClickListener { dialog.dismiss() }
        binding.btnContinue.setOnClickListener { viewModel.logout() }
    }
}