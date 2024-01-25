package com.synrgy.aeroswift.presentation.fragment.checkout

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.synrgy.aeroswift.R
import com.synrgy.aeroswift.databinding.FragmentAddBaggageBinding


class AddBaggageFragment : Fragment() {
    private lateinit var binding: FragmentAddBaggageBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentAddBaggageBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.toolbarAddBaggage.setNavigationOnClickListener {
            requireActivity().onBackPressed()
        }

        binding.btnDone.setOnClickListener {
            requireActivity().onBackPressed()
        }

        binding.tvDepartSelect.setOnClickListener {
            findNavController().navigate(R.id.action_addBaggageFragment_to_addDepartBaggageFragment)
        }
    }
}