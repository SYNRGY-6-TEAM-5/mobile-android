package com.synrgy.aeroswift.presentation.fragment.checkin

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.synrgy.aeroswift.R
import com.synrgy.aeroswift.databinding.FragmentCheckInDetailsBinding
import com.synrgy.aeroswift.presentation.adapter.AddonTableAdapter
import com.synrgy.aeroswift.presentation.adapter.TicketOrderAdapter
import com.synrgy.presentation.helper.Helper


class CheckInDetailsFragment : Fragment() {
    private lateinit var binding: FragmentCheckInDetailsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentCheckInDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        handleSetAdapter()

        binding.toolbarCheckInDetails.setNavigationOnClickListener {
            requireActivity().onBackPressed()
        }

        binding.ivCopy.setOnClickListener {
            val text = binding.tvBookingCode.text.toString()
            Helper.copyToClipboard(requireContext(), "booking_code", text)
            Toast.makeText(requireContext(), "Code copied!", Toast.LENGTH_LONG).show()
        }

        binding.btnCheckIn.setOnClickListener {
            findNavController().navigate(R.id.action_checkInDetailsFragment_to_checkInPolicyFragment)
        }
    }

    private fun handleSetAdapter() {
        val ticketAdapter = TicketOrderAdapter()
        binding.rvCheckIn.layoutManager = LinearLayoutManager(requireActivity())
        binding.rvCheckIn.adapter = ticketAdapter

        val addonAdapter = AddonTableAdapter()
        binding.rvAddons.layoutManager = LinearLayoutManager(requireActivity())
        binding.rvAddons.adapter = addonAdapter
    }
}