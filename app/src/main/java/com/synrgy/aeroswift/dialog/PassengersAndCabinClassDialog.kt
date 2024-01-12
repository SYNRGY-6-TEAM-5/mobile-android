package com.synrgy.aeroswift.dialog

import android.app.Activity
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModel
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.button.MaterialButton
import com.google.android.material.chip.Chip
import com.synrgy.aeroswift.R
import com.synrgy.aeroswift.databinding.DialogPassengersAndClassBinding
import com.synrgy.aeroswift.models.RvPassengerModels
import com.synrgy.aeroswift.presentation.adapter.SelectPassengerAdapter
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

            val passengers = mutableListOf(
                RvPassengerModels("Adult", "+12 years old", 0),
                RvPassengerModels("Child", "2-11 years old", 0),
                RvPassengerModels("Infant", "0-23 months old", 0),
            )

            val adapter = SelectPassengerAdapter(passengers)
            binding.rvPassengers.adapter = adapter

            binding.btnSave.setOnClickListener {
                val selectedClass: Chip? =
                    binding.cgPassengers.findViewById(binding.cgPassengers.checkedChipId)
                val sumCount = passengers.sumOf { it.count }

                if (selectedClass == null) {
                    Toast.makeText(activity, "Please select cabin class", Toast.LENGTH_SHORT)
                        .show()
                    return@setOnClickListener
                } else if (sumCount == 0) {
                    Toast.makeText(activity, "Please select passenger", Toast.LENGTH_SHORT)
                        .show()
                    return@setOnClickListener
                } else {
                    selectedClass.let {
                        homeViewModel.setSelectedClass(it.text.toString())
                    }
                    sumCount.let {
                        homeViewModel.setTotalPassengers(it)
                    }
                    dismiss()
                }

            }



            setCancelable(true)
            show()
        }

    }
}