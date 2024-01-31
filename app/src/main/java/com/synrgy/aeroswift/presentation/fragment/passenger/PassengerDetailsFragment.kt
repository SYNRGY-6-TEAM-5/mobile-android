package com.synrgy.aeroswift.presentation.fragment.passenger

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.synrgy.aeroswift.databinding.FragmentPassengerDetailsBinding
import com.synrgy.aeroswift.dialog.ConfirmationDialog
import com.synrgy.aeroswift.dialog.LoadingDialog
import com.synrgy.aeroswift.presentation.viewmodel.auth.AuthViewModel
import com.synrgy.aeroswift.presentation.viewmodel.passenger.PassengerDetailsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PassengerDetailsFragment : Fragment() {
    private lateinit var binding: FragmentPassengerDetailsBinding

    private lateinit var loadingDialog: LoadingDialog
    private lateinit var confirmationDialog: ConfirmationDialog

    private val passengerViewModel: PassengerDetailsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentPassengerDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loadingDialog = LoadingDialog(requireActivity())
        confirmationDialog = ConfirmationDialog(requireActivity()) { passengerViewModel.deletePassenger() }

        observeViewModel()

        binding.btnDelete.setOnClickListener {
            confirmationDialog.show(
                heading = "Delete Confirmation",
                title = "Delete passenger data?",
                description = "When you book later, you need to manually enter the profile details"
            )
        }
        binding.btnSave.setOnClickListener { requireActivity().onBackPressed() }
        binding.toolbarPassenger.setNavigationOnClickListener { requireActivity().onBackPressed() }
    }

    private fun observeViewModel() {
        passengerViewModel.loading.observe(viewLifecycleOwner) {
            if (it) {
                loadingDialog.startLoadingDialog()
            } else {
                loadingDialog.dismissDialog()
                confirmationDialog.dismiss()
                requireActivity().onBackPressed()
            }
        }
    }
}