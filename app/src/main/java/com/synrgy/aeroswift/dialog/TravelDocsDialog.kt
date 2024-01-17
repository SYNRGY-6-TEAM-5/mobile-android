package com.synrgy.aeroswift.dialog

import android.app.Activity
import android.view.View
import android.view.WindowManager
import androidx.fragment.app.DialogFragment
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.button.MaterialButton
import com.synrgy.aeroswift.R

class TravelDocsDialog(
    private val activity: Activity
): DialogFragment() {
    private lateinit var dialog: BottomSheetDialog

    fun show() {
        dialog = BottomSheetDialog(activity)
        val inflater = activity.layoutInflater
        val view = inflater.inflate(R.layout.dialog_travel_docs, null)

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

        val navHostFragment = parentFragmentManager.findFragmentById(R.id.nav_container_checkout) as NavHostFragment
        val navController = navHostFragment.findNavController()

        val btnContinue = view?.findViewById<MaterialButton>(R.id.btn_continue)

        btnContinue?.setOnClickListener {
            navController.navigate(R.id.action_passengerDomFragment_to_passengerIntFragment)
        }
    }

    private fun setupFullHeight(bottomSheet: View) {
        val layoutParams = bottomSheet.layoutParams
        layoutParams.height = WindowManager.LayoutParams.MATCH_PARENT
        bottomSheet.layoutParams = layoutParams
    }
}