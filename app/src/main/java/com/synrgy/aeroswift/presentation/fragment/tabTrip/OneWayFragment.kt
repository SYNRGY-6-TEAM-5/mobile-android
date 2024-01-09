package com.synrgy.aeroswift.presentation.fragment.tabTrip

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.fragment.app.Fragment
import androidx.transition.R.*
import com.synrgy.aeroswift.R
import com.synrgy.aeroswift.databinding.FragmentOneWayBinding

class OneWayFragment : Fragment() {

    private lateinit var binding: FragmentOneWayBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentOneWayBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val spOrigin = binding.spOrigin
        val spDestination = binding.spDestination

        initSpinner(spDestination)
        initSpinner(spOrigin)

        binding.btnSwap.setOnClickListener {
            val temp = spOrigin.selectedItemPosition
            spOrigin.setSelection(spDestination.selectedItemPosition)
            spDestination.setSelection(temp)
        }
    }

    private fun initSpinner(spinner: Spinner) {
        val adapter = ArrayAdapter.createFromResource(
            requireContext(),
            R.array.origin,
            android.R.layout.simple_spinner_item
        )

        spinner.adapter = adapter
        spinner.setSelection(0)
    }

}