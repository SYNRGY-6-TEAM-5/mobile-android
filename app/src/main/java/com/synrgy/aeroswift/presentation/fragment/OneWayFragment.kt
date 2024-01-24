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
import com.synrgy.aeroswift.presentation.viewmodel.airport.AirportListViewModel
import com.synrgy.domain.response.airport.AirportData
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OneWayFragment : Fragment() {

    private lateinit var binding: FragmentOneWayBinding

    private lateinit var airportListDialog: AirportListDialog
    private lateinit var loadingDialog: LoadingDialog

    private val airportViewModel: AirportListViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentOneWayBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loadingDialog = LoadingDialog(requireActivity())
        airportListDialog = AirportListDialog(requireActivity(), airportViewModel, viewLifecycleOwner)

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
            if (binding.spOrigin.text == getString(R.string.select)) {
                binding.spDestination.setTextColor(ContextCompat.getColor(requireContext(), R.color.gray_200))
                binding.spOrigin.setTextColor(ContextCompat.getColor(requireContext(), R.color.base_black))
            }
            if (binding.spDestination.text == getString(R.string.select)) {
                binding.spOrigin.setTextColor(ContextCompat.getColor(requireContext(), R.color.gray_200))
                binding.spDestination.setTextColor(ContextCompat.getColor(requireContext(), R.color.base_black))
            }

            val temp = binding.spOrigin.text
            binding.spOrigin.text = binding.spDestination.text
            binding.spDestination.text = temp
        }
    }

    private fun observeViewModel() {
        airportViewModel.loading.observe(viewLifecycleOwner, ::handleLoading)
        airportViewModel.origin.observe(viewLifecycleOwner, ::handleGetOrigin)
        airportViewModel.destination.observe(viewLifecycleOwner, ::handleGetDestination)
    }

    private fun handleLoading(loading: Boolean) {
        if (loading) {
            loadingDialog.startLoadingDialog()
        } else {
            loadingDialog.dismissDialog()
            airportListDialog.show()
        }
    }

    private fun handleGetOrigin(data: AirportData) {
        binding.spOrigin.text = data.iataCode
        binding.spOrigin.setTextColor(ContextCompat.getColor(requireContext(), R.color.base_black))
    }

    private fun handleGetDestination(data: AirportData) {
        binding.spDestination.text = data.iataCode
        binding.spDestination.setTextColor(ContextCompat.getColor(requireContext(), R.color.base_black))
    }
}