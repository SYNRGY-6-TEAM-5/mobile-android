package com.synrgy.aeroswift.presentation.fragment

import android.app.DatePickerDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import androidx.fragment.app.viewModels
import com.google.android.material.tabs.TabLayoutMediator
import com.synrgy.aeroswift.databinding.FragmentHomeBinding
import com.synrgy.aeroswift.dialog.PassengersAndCabinClassDialog
import com.synrgy.aeroswift.presentation.adapter.TabTripAdapter
import com.synrgy.aeroswift.presentation.viewmodel.HomeViewModel
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding

    private val viewModel: HomeViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val tabAdapter = TabTripAdapter(requireActivity())
        binding.viewPager.adapter = tabAdapter

        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            when (position) {
                0 -> tab.text = "One-Way"
                else -> tab.text = "Roundtrip"
            }
        }.attach()

        binding.clSelectDate.setOnClickListener {
            showDatePicker()
        }

        binding.clPassengerAndCabinClass.setOnClickListener {
            PassengersAndCabinClassDialog(requireActivity(), viewModel).show()
        }

        val tvSelectedClass = binding.tvSelectedPassengerAndCabinClass

        viewModel.selectedClass.observe(viewLifecycleOwner) {
            tvSelectedClass.text = it
        }
    }

    private fun showDatePicker() {
        // Create the DatePickerFragment
        val datePickerDialog = DatePickerDialog(
            requireContext(),
            { _: DatePicker, year: Int, month: Int, dayOfMonth: Int ->
                // Set the selected date in the TextView
                val calendar = Calendar.getInstance()
                calendar.set(year, month, dayOfMonth)
                val simpleDateFormat = SimpleDateFormat("dd MMMM yyyy", Locale.getDefault())
                val date = simpleDateFormat.format(calendar.time)
                binding.tvSelectedDate.text = date
            },
            // Set the DatePickerDialog to open on the last selected date of the user
            Calendar.getInstance().get(Calendar.YEAR),
            Calendar.getInstance().get(Calendar.MONTH),
            Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
        )
        // Show the DatePickerDialog
        datePickerDialog.show()
    }

}