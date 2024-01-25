package com.synrgy.aeroswift.presentation.fragment.checkout

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.synrgy.aeroswift.R
import com.synrgy.aeroswift.databinding.FragmentPassengerBinding
import com.synrgy.aeroswift.dialog.TravelDocsDialog

class PassengerFragment : Fragment() {
    private lateinit var binding: FragmentPassengerBinding

    private lateinit var travelDocsDialog: TravelDocsDialog

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

        binding.passengerBaby.setOnClickListener {
            findNavController().navigate(R.id.action_passengerFragment_to_passengerDomFragment)
        }

        binding.passengerAdult.setOnClickListener { travelDocsDialog.show() }

        binding.btnNext.setOnClickListener { handleNavigate() }

        binding.toolbarPassenger.setNavigationOnClickListener {
            requireActivity().onBackPressed()
        }
    }

    private fun handleNavigate() {
        findNavController().navigate(R.id.action_passengerFragment_to_addonsFragment)
    }
}