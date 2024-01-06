package com.synrgy.aeroswift.dialog

import android.app.Activity
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.button.MaterialButton
import com.synrgy.aeroswift.R
import com.synrgy.aeroswift.presentation.LoginActivity
import com.synrgy.aeroswift.presentation.viewmodel.MainViewModel

class PermissionNotificationDialog(
    private val activity: Activity,
    private val mainViewModel: MainViewModel
) {
    private lateinit var dialog: BottomSheetDialog

    fun show() {
        dialog = BottomSheetDialog(activity)
        val inflater = activity.layoutInflater
        val view = inflater.inflate(R.layout.dialog_notification_permission, null)

        dialog.setContentView(view)
        dialog.setCancelable(true)
        dialog.show()

        val btnContinue = view?.findViewById<MaterialButton>(R.id.btn_notif_perm)

        btnContinue?.setOnClickListener {
            mainViewModel.setNewUser(false)
            LoginActivity.startActivity(activity)
            activity.finish()
        }
    }
}