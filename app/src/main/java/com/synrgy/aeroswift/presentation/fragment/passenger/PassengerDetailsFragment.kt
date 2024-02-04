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
import com.synrgy.aeroswift.presentation.viewmodel.passenger.PassengerDetailsViewModel
import com.synrgy.domain.local.PassengerData
import com.synrgy.presentation.constant.Constant
import com.synrgy.presentation.helper.Helper
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

@AndroidEntryPoint
class PassengerDetailsFragment : Fragment() {
    companion object {
        const val KEY_PASSENGER_DETAIL_ID = "key_passenger_detail_id"
    }

    private lateinit var binding: FragmentPassengerDetailsBinding

    private lateinit var loadingDialog: LoadingDialog
    private lateinit var confirmationDialog: ConfirmationDialog

    private val passengerViewModel: PassengerDetailsViewModel by viewModels()

    private val dateFormatter = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
    private var selectedDate = Calendar.getInstance()

    private var passengerId: String? = null

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

        passengerId = arguments?.getString(KEY_PASSENGER_DETAIL_ID)

        if (passengerId != null) passengerViewModel.getPassenger(passengerId!!)
        observeViewModel()

        binding.btnDelete.setOnClickListener {
            confirmationDialog.show(
                heading = "Delete Confirmation",
                title = "Delete passenger data?",
                description = "When you book later, you need to manually enter the profile details"
            )
        }
        binding.btnSave.setOnClickListener { handleSavePassenger() }
        binding.toolbarPassenger.setNavigationOnClickListener { handleNavigate() }

        binding.tiBirth.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) Helper.showDatePicker(requireContext(), selectedDate, ::updateBirthInput)
        }
        binding.tiExpiry.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) Helper.showDatePicker(requireContext(), selectedDate, ::updateBirthInput)
        }
    }

    private fun observeViewModel() {
        passengerViewModel.passenger.observe(viewLifecycleOwner, ::handleGetPassenger)
        passengerViewModel.loading.observe(viewLifecycleOwner, ::handleLoading)
        passengerViewModel.success.observe(viewLifecycleOwner, ::handleSuccess)
    }

    private fun handleNavigate() {
        requireActivity().supportFragmentManager.popBackStack()
    }

    private fun handleLoading(loading: Boolean) {
        if (loading) {
            loadingDialog.startLoadingDialog()
        } else {
            loadingDialog.dismissDialog()
            confirmationDialog.dismiss()
            handleNavigate()
        }
    }

    private fun updateBirthInput() {
        binding.tiBirth.setText(dateFormatter.format(selectedDate.time))
    }

    private fun handleGetPassenger(data: PassengerData) {
        binding.tiNik.setText(data.nik)
        binding.tiName.setText(data.name)
        binding.tiBirth.setText(data.dob)
    }

    private fun handleSuccess(success: Boolean) {
        if (success) {
            loadingDialog.dismissDialog()
            Helper.showToast(requireActivity(), requireContext(), "Save data success!", true)
            handleNavigate()
        }
    }

    private fun handleSavePassenger() {
        val nik = binding.tiNik.text.toString()
        val name = binding.tiName.text.toString()
        val dob = binding.tiBirth.text.toString()

        val data = PassengerData(
            id = passengerId ?: Date().time.toString(),
            nik = nik,
            name = name,
            dob = dob,
            category = Constant.PassengerType.ADULT.value
        )

        passengerViewModel.addPassenger(data)
    }
}