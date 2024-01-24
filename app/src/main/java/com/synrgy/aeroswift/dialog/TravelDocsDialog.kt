package com.synrgy.aeroswift.dialog

import android.app.Activity
import android.view.View
import android.view.WindowManager
import androidx.fragment.app.DialogFragment
import androidx.navigation.NavController
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.synrgy.aeroswift.R
import com.synrgy.aeroswift.databinding.DialogTravelDocsBinding

class TravelDocsDialog(
    private val activity: Activity,
    private val navController: NavController
): DialogFragment() {
    private lateinit var dialog: BottomSheetDialog
    private lateinit var binding: DialogTravelDocsBinding

    fun show() {
        dialog = BottomSheetDialog(activity)
        binding = DialogTravelDocsBinding.inflate(activity.layoutInflater)
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

        binding.btnContinue.setOnClickListener {
            dialog.dismiss()
            navController.navigate(R.id.action_passengerFragment_to_passengerIntFragment)
        }

        binding.ivClose.setOnClickListener { dialog.dismiss() }
    }

    private fun setupFullHeight(bottomSheet: View) {
        val layoutParams = bottomSheet.layoutParams
        layoutParams.height = WindowManager.LayoutParams.MATCH_PARENT
        bottomSheet.layoutParams = layoutParams
    }
}