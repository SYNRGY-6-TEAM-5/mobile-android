package com.synrgy.aeroswift.dialog

import android.app.Activity
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.synrgy.aeroswift.databinding.DialogPassengerBinding
import com.synrgy.aeroswift.presentation.adapter.PassengerAdapter
import com.synrgy.aeroswift.presentation.viewmodel.passenger.PassengerDetailsViewModel
import com.synrgy.domain.local.PassengerData
import com.synrgy.presentation.constant.PassengerConstant

class PassengerDialog(
    private val activity: Activity,
    private val viewLifecycleOwner: LifecycleOwner,
    private val viewModel: PassengerDetailsViewModel
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

        viewModel.getPassengers()
        observeViewModel()

        binding.ivClose.setOnClickListener { dialog.dismiss() }
        binding.btnNewPassenger.setOnClickListener { clickListener() }
    }

    fun dismiss() {
        dialog.dismiss()
    }

    private fun observeViewModel() {
        viewModel.passengers.observe(viewLifecycleOwner, ::handleSetAdapter)
    }

    private fun handleSetAdapter(data: List<PassengerData>) {
        binding.rvPassenger.layoutManager = LinearLayoutManager(activity)
        binding.rvPassenger.adapter = this.adapter
        this.adapter.submitList(data)
    }

    private fun handleClickPassenger(data: PassengerData) {
        dismiss()
    }
}