package com.synrgy.aeroswift.dialog

import android.app.Activity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.synrgy.aeroswift.databinding.DialogPassengerBinding
import com.synrgy.aeroswift.presentation.adapter.PassengerAdapter
import com.synrgy.domain.local.PassengerData
import com.synrgy.presentation.constant.PassengerConstant

class PassengerDialog(
    private val activity: Activity
) {
    private lateinit var dialog: BottomSheetDialog
    private lateinit var binding: DialogPassengerBinding

    private val adapter = PassengerAdapter(::handleClickPassenger)

    fun show(
        clickListener: () -> Unit
    ) {
        dialog = BottomSheetDialog(activity)
        binding = DialogPassengerBinding.inflate(activity.layoutInflater)
        val view = binding.root

        dialog.setContentView(view)
        dialog.setCancelable(true)
        dialog.show()

        handleSetAdapter()

        binding.ivClose.setOnClickListener { dialog.dismiss() }
        binding.btnNewPassenger.setOnClickListener { clickListener() }
    }

    fun dismiss() {
        dialog.dismiss()
    }

    private fun handleSetAdapter() {
        binding.rvPassenger.layoutManager = LinearLayoutManager(activity)
        binding.rvPassenger.adapter = this.adapter
        this.adapter.submitList(PassengerConstant.getData())
    }

    private fun handleClickPassenger(data: PassengerData) {
        dismiss()
    }
}