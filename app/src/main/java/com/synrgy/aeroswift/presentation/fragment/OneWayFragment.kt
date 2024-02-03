package com.synrgy.aeroswift.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.synrgy.aeroswift.R
import com.synrgy.aeroswift.databinding.FragmentOneWayBinding
import com.synrgy.aeroswift.dialog.AirportListDialog
import com.synrgy.aeroswift.dialog.LoadingDialog
import com.synrgy.aeroswift.presentation.viewmodel.HomeViewModel
import com.synrgy.aeroswift.presentation.viewmodel.airport.AirportListViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OneWayFragment(
    private val homeViewModel: HomeViewModel
) : Fragment() {

    private lateinit var binding: FragmentOneWayBinding

    private lateinit var airportListDialog: AirportListDialog
    private lateinit var loadingDialog: LoadingDialog

    private val airportViewModel: AirportListViewModel by viewModels()

    private var blackColor = 0
    private var lightGrayColor = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentOneWayBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        blackColor = ContextCompat.getColor(requireContext(), R.color.base_black)
        lightGrayColor = ContextCompat.getColor(requireContext(), R.color.gray_200)

        loadingDialog = LoadingDialog(requireActivity())
        airportListDialog = AirportListDialog(
            requireActivity(),
            airportViewModel,
            homeViewModel,
            viewLifecycleOwner
        )

        observeViewModel()

        binding.spOrigin.setOnClickListener {
            airportViewModel.getAirport()
            airportViewModel.setIsDest(false)
        }
        binding.spDestination.setOnClickListener {
            airportViewModel.getAirport()
            airportViewModel.setIsDest(true)
        }

        binding.btnSwap.setOnClickListener {
            val origin = binding.spOrigin.text.toString()
            val destination = binding.spDestination.text.toString()

            homeViewModel.setOrigin(destination)
            homeViewModel.setDestination(origin)
        }
    }

    private fun observeViewModel() {
        airportViewModel.loading.observe(viewLifecycleOwner, ::handleLoading)
        homeViewModel.origin.observe(viewLifecycleOwner, ::handleGetOrigin)
        homeViewModel.destination.observe(viewLifecycleOwner, ::handleGetDestination)
    }

    private fun handleLoading(loading: Boolean) {
        if (loading) {
            loadingDialog.startLoadingDialog()
        } else {
            loadingDialog.dismissDialog()
            airportListDialog.show()
        }
    }

    private fun handleGetOrigin(data: String) {
        if (data.isNotEmpty() && data.isNotBlank()) {
            binding.spOrigin.text = data
            if (binding.spOrigin.text == getString(R.string.select)) {
                binding.spOrigin.setTextColor(lightGrayColor)
            } else {
                binding.spOrigin.setTextColor(blackColor)
            }
        }
    }

    private fun handleGetDestination(data: String) {
        if (data.isNotEmpty() && data.isNotBlank()) {
            binding.spDestination.text = data
            if (binding.spDestination.text == getString(R.string.select)) {
                binding.spDestination.setTextColor(lightGrayColor)
            } else {
                binding.spDestination.setTextColor(blackColor)
            }
        }
    }
}