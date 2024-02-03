package com.synrgy.aeroswift.presentation.fragment.checkout

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.synrgy.aeroswift.R
import com.synrgy.aeroswift.databinding.FragmentPassengerBinding
import com.synrgy.aeroswift.dialog.PassengerDialog
import com.synrgy.aeroswift.dialog.TravelDocsDialog
import com.synrgy.aeroswift.presentation.viewmodel.HomeViewModel
import com.synrgy.aeroswift.presentation.viewmodel.auth.AuthViewModel
import com.synrgy.domain.local.FlightSearch
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
        passengerDialog = PassengerDialog(requireActivity())

        authViewModel.getUser()
        authViewModel.checkAuth()
        homeViewModel.getFlightSearch()
        observeViewModel()

        binding.passengerBaby.setOnClickListener {
            passengerDialog.show {
                passengerDialog.dismiss()
                findNavController().navigate(R.id.action_passengerFragment_to_passengerDomFragment)
            }
        }

        binding.passengerAdult.setOnClickListener {
            passengerDialog.show {
                passengerDialog.dismiss()
                travelDocsDialog.show()
            }
        }

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
        }
    }

    private fun handleLoading(loading: Boolean) {
        if (loading) binding.layoutFlight.loadSkeleton() else binding.layoutFlight.hideSkeleton()
    }

    private fun handleContactLoading(loading: Boolean) {
        if (loading) binding.layoutContact.loadSkeleton() else binding.layoutContact.hideSkeleton()
    }
}