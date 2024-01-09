package com.synrgy.aeroswift.dialog

import android.app.Activity
import android.widget.Toast
import androidx.lifecycle.ViewModel
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.button.MaterialButton
import com.google.android.material.chip.Chip
import com.synrgy.aeroswift.R
import com.synrgy.aeroswift.databinding.DialogPassengersAndClassBinding
import com.synrgy.aeroswift.presentation.viewmodel.HomeViewModel

class PassengersAndCabinClassDialog(
    private val activity: Activity,
    private val homeViewModel: HomeViewModel
) {

    private lateinit var binding: DialogPassengersAndClassBinding

    fun show() {
        BottomSheetDialog(activity).apply {
            binding = DialogPassengersAndClassBinding.inflate(layoutInflater)
            setContentView(binding.root)
            binding.btnClose.setOnClickListener {
                dismiss()
            }

            binding.btnSave.setOnClickListener {
                val selectedClass: Chip? =
                    binding.cgPassengers.findViewById(binding.cgPassengers.checkedChipId)
                if (selectedClass == null) {
                    Toast.makeText(activity, "Please select cabin class", Toast.LENGTH_SHORT)
                        .show()
                    return@setOnClickListener
                } else {
                    selectedClass.let {
                        homeViewModel.setSelectedClass(it?.text.toString())
                    }
                    dismiss()
                }
            }
            setCancelable(true)
            show()
        }

    }
}