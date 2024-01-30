package com.synrgy.aeroswift.presentation.fragment.flightorder

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.synrgy.aeroswift.R
import com.synrgy.aeroswift.databinding.FragmentFlightOrderBinding
import com.synrgy.aeroswift.presentation.HomeActivity
import com.synrgy.aeroswift.presentation.adapter.FlightOrderAdapter
import com.synrgy.domain.FlightOrder
import com.synrgy.presentation.constant.FlightOrderConstant


class FlightOrderFragment : Fragment() {
    private lateinit var binding: FragmentFlightOrderBinding

    private val adapter = FlightOrderAdapter(::handleClickFlightOrder)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentFlightOrderBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                HomeActivity.startProfileFragment(requireActivity())
                requireActivity().finish()
            }
        })

        handleSetAdapter()

        binding.toolbarFlight.setNavigationOnClickListener { requireActivity().onBackPressed() }
    }

    private fun handleSetAdapter() {
        binding.rvFlight.layoutManager = LinearLayoutManager(requireActivity())
        binding.rvFlight.adapter = this.adapter
        this.adapter.submitList(FlightOrderConstant.getData())
    }

    private fun handleClickFlightOrder(data: FlightOrder) {
        val bundle = Bundle()
        bundle.putString(PaymentHistoryFragment.KEY_PAYMENT_CATEGORY, data.title)

        findNavController().navigate(R.id.action_flightOrderFragment_to_paymentHistoryFragment, bundle)
    }
}