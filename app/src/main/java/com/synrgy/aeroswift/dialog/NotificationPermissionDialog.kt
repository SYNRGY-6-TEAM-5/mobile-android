package com.synrgy.aeroswift.dialog

import android.app.Activity
import android.view.View
import android.view.WindowManager
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.button.MaterialButton
import com.synrgy.aeroswift.R
import com.synrgy.aeroswift.databinding.DialogNotificationPermissionBinding
import com.synrgy.aeroswift.presentation.AuthActivity
import com.synrgy.aeroswift.presentation.viewmodel.MainViewModel

class NotificationPermissionDialog(
    private val activity: Activity,
    private val mainViewModel: MainViewModel
) {
    private lateinit var dialog: BottomSheetDialog
    private lateinit var binding: DialogNotificationPermissionBinding

    fun show() {
        dialog = BottomSheetDialog(activity)
        binding = DialogNotificationPermissionBinding.inflate(activity.layoutInflater)
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

        binding.btnNotifPerm.setOnClickListener {
            mainViewModel.setNewUser(false)
            AuthActivity.startActivity(activity)
            activity.finish()
        }
    }

    private fun setupFullHeight(bottomSheet: View) {
        val layoutParams = bottomSheet.layoutParams
        layoutParams.height = WindowManager.LayoutParams.MATCH_PARENT
        bottomSheet.layoutParams = layoutParams
    }
}