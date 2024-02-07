package com.synrgy.aeroswift.presentation.fragment.checkin

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.synrgy.aeroswift.databinding.FragmentCheckInDataBinding
import com.synrgy.aeroswift.dialog.LoadingDialog
import com.synrgy.aeroswift.models.AddonModels
import com.synrgy.aeroswift.presentation.CheckInSuccessActivity
import com.synrgy.aeroswift.presentation.adapter.AddonAdapter
import com.synrgy.aeroswift.presentation.viewmodel.checkin.CheckInViewModel
import com.synrgy.aeroswift.presentation.viewmodel.passenger.PassengerDetailsViewModel
import com.synrgy.domain.local.PassengerData
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class CheckInDataFragment : Fragment() {
    private lateinit var binding: FragmentCheckInDataBinding

    private val passengerViewModel: PassengerDetailsViewModel by viewModels()
    private val checkInViewModel: CheckInViewModel by viewModels()

    private lateinit var loadingDialog: LoadingDialog

    private val adapter = AddonAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentCheckInDataBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loadingDialog = LoadingDialog(requireActivity())

        binding.rvCheckIn.layoutManager = LinearLayoutManager(requireActivity())
        binding.rvCheckIn.adapter = this.adapter

        passengerViewModel.getPassengers()
        observeViewModel()

        binding.toolbarCheckInData.setNavigationOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
        }
        binding.btnCheckIn.setOnClickListener { checkInViewModel.checkIn() }
    }

    private fun observeViewModel() {
        passengerViewModel.passengers.observe(viewLifecycleOwner, ::handleGetPassengers)
        checkInViewModel.loading.observe(viewLifecycleOwner, ::handleLoading)
    }

    private fun handleGetPassengers(data: List<PassengerData>) {
        if (data.isNotEmpty()) {
            val passengers = data.map {
                AddonModels(
                    id = it.id,
                    name = it.name,
                    count = "B3",
                    isSeat = true
                )
            }
            this.adapter.submitList(passengers)
        }
    }

    private fun handleLoading(loading: Boolean) {
        if (loading) {
            loadingDialog.startLoadingDialog()
        } else {
            loadingDialog.dismissDialog()
            handleNavigateSuccess()
        }
    }

    private fun handleNavigateSuccess() {
        CheckInSuccessActivity.startActivity(requireActivity())
        requireActivity().finish()
    }
}