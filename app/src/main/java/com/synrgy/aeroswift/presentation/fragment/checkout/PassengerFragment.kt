package com.synrgy.aeroswift.presentation.fragment.checkout

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.synrgy.aeroswift.R
import com.synrgy.aeroswift.databinding.FragmentPassengerBinding
import com.synrgy.aeroswift.dialog.PassengerDialog
import com.synrgy.aeroswift.dialog.TravelDocsDialog
import com.synrgy.aeroswift.presentation.adapter.PassengerAdapter
import com.synrgy.aeroswift.presentation.viewmodel.HomeViewModel
import com.synrgy.aeroswift.presentation.viewmodel.auth.AuthViewModel
import com.synrgy.aeroswift.presentation.viewmodel.passenger.PassengerDetailsViewModel
import com.synrgy.domain.local.FlightSearch
import com.synrgy.domain.local.PassengerData
import com.synrgy.presentation.constant.Constant
import com.synrgy.presentation.helper.Helper
import dagger.hilt.android.AndroidEntryPoint
import koleton.api.hideSkeleton
import koleton.api.loadSkeleton

@AndroidEntryPoint
class PassengerFragment : Fragment() {
    private lateinit var binding: FragmentPassengerBinding

    private lateinit var travelDocsDialog: TravelDocsDialog
    private lateinit var passengerDialog: PassengerDialog

    private val authViewModel: AuthViewModel by viewModels()
    private val homeViewModel: HomeViewModel by viewModels()
    private val passengerViewModel: PassengerDetailsViewModel by viewModels()

    private val adapter = PassengerAdapter(::handleClickPassenger)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentPassengerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        travelDocsDialog = TravelDocsDialog(requireActivity(), findNavController())
        passengerDialog = PassengerDialog(requireActivity(), viewLifecycleOwner, passengerViewModel)

        authViewModel.getUser()
        authViewModel.checkAuth()
        homeViewModel.getFlightSearch()
        observeViewModel()

        binding.rvPassenger.layoutManager = LinearLayoutManager(requireActivity())

        binding.btnNext.setOnClickListener { handleNavigate() }

        binding.toolbarPassenger.setNavigationOnClickListener {
            requireActivity().onBackPressed()
        }
    }

    private fun handleNavigate() {
        findNavController().navigate(R.id.action_passengerFragment_to_addonsFragment)
    }

    private fun observeViewModel() {
        homeViewModel.flightSearch.observe(viewLifecycleOwner, ::handleFlightSearch)
        homeViewModel.loading.observe(viewLifecycleOwner, ::handleLoading)

        authViewModel.loading.observe(viewLifecycleOwner, ::handleContactLoading)
        authViewModel.name.observe(viewLifecycleOwner) { binding.tvName.text = it }
        authViewModel.email.observe(viewLifecycleOwner) { binding.tvEmail.text = it }
        authViewModel.phoneNumber.observe(viewLifecycleOwner) {
            binding.tvPhone.text = Helper.formatPhoneNumber("+62$it")
        }
    }

    private fun handleFlightSearch(data: FlightSearch) {
        if (Helper.isValidFlightSearch(data)) {
            binding.tv1Origin.text = data.origin
            binding.tv1Dest.text = data.destination

            handleSetPassengerAdapter(data)
        }
    }

    private fun handleLoading(loading: Boolean) {
        if (loading) binding.layoutFlight.loadSkeleton() else binding.layoutFlight.hideSkeleton()
    }

    private fun handleContactLoading(loading: Boolean) {
        if (loading) binding.layoutContact.loadSkeleton() else binding.layoutContact.hideSkeleton()
    }

    private fun handleSetPassengerAdapter(data: FlightSearch) {
        val passengers = arrayListOf<PassengerData>()

        val startAdult = if (data.adultSeat != 0) 1 else 0
        val startChild = if (data.childSeat != 0) {
            data.adultSeat?.let { it + 1 } ?: 1
        } else 0
        val startInfant = when {
            data.infantSeat != 0 -> {
                when {
                    data.adultSeat != 0 && data.childSeat != 0 -> data.adultSeat!! + data.childSeat!! + 1
                    data.adultSeat != 0 -> data.adultSeat!! + 1
                    data.childSeat != 0 -> data.childSeat!! + 1
                    else -> 1
                }
            }
            else -> 0
        }

        if (startAdult != 0) {
            for (i in startAdult..data.adultSeat!!) {
                passengers.add(
                    PassengerData(
                        id = "adult-$i",
                        name = "Passenger $i (Adult)",
                        category = Constant.PassengerType.ADULT.value
                    )
                )
            }
        }

        if (startChild != 0) {
            for (i in startChild..(startChild - 1) + data.childSeat!!) {
                passengers.add(
                    PassengerData(
                        id = "child-$i",
                        name = "Passenger $i (Child)",
                        category = Constant.PassengerType.CHILD.value
                    )
                )
            }
        }

        if (startInfant != 0) {
            for (i in startInfant..(startInfant - 1) + data.infantSeat!!) {
                passengers.add(
                    PassengerData(
                        id = "infant-$i",
                        name = "Passenger $i (Infant)",
                        category = Constant.PassengerType.INFANT.value
                    )
                )
            }
        }

        binding.rvPassenger.adapter = this.adapter
        this.adapter.submitList(passengers)
    }

    private fun handleClickPassenger(data: PassengerData) {
        passengerDialog.show {
            passengerDialog.dismiss()
            travelDocsDialog.show(data)
        }
    }
}