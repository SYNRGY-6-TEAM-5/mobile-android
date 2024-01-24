package com.synrgy.aeroswift.presentation.fragment

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.synrgy.aeroswift.R
import com.synrgy.aeroswift.databinding.FragmentHomeBinding
import com.synrgy.aeroswift.dialog.PassengersAndCabinClassDialog
import com.synrgy.aeroswift.presentation.FlightDetailsActivity
import com.synrgy.aeroswift.presentation.adapter.DiscountAdapter
import com.synrgy.aeroswift.presentation.adapter.TabTripAdapter
import com.synrgy.aeroswift.presentation.adapter.TicketPromoAdapter
import com.synrgy.aeroswift.presentation.viewmodel.HomeViewModel
import com.synrgy.aeroswift.presentation.viewmodel.auth.AuthViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding

    private val viewModel: HomeViewModel by viewModels()
    private val authViewModel: AuthViewModel by viewModels()

    private var selectedClass: String? = null
    private var totalPassenger: String? = null

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

        adapter()
        tabController()

        binding.clDepartDate.setOnClickListener {
            showDatePicker(binding.tvSelectedDate)
        }

        binding.clReturnDate.setOnClickListener {
            showDatePicker(binding.tvSelectedReturnDate)
        }

        binding.clPassengerAndCabinClass.setOnClickListener {
            PassengersAndCabinClassDialog(requireActivity(), viewModel).show()
        }
        val tvSelectedClass = binding.tvSelectedPassengerAndCabinClass

        viewModel.selectedClass.observe(viewLifecycleOwner) {
            selectedClass = it
            tvSelectedClass.text = "$totalPassenger Passengers - $selectedClass"
        }

        viewModel.totalPassengers.observe(viewLifecycleOwner) {
            totalPassenger = it.toString()
            tvSelectedClass.text = "$totalPassenger Passengers - $selectedClass"
        }

        authViewModel.checkAuth()

        authViewModel.name.observe(viewLifecycleOwner) {
            binding.tvName.text = it
        }

        authViewModel.photo.observe(viewLifecycleOwner) {
            if (it.isNotBlank() && it.isNotEmpty()) {
                Glide
                    .with(this)
                    .load(it)
                    .centerCrop()
                    .circleCrop()
                    .into(binding.ivProfile)
            }
        }

        binding.btnSearchFlight.setOnClickListener {
            FlightDetailsActivity.startActivity(requireActivity())
        }
    }

    private fun tabController() {
        val clDepartDate = binding.clDepartDate
        val clReturnDate = binding.clReturnDate
        val params = clDepartDate.layoutParams

        binding.viewPager.isUserInputEnabled = false

        binding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                tab?.position?.let { binding.viewPager.setCurrentItem(it, false) }

                if (tab?.position == 0) {
                    params.width = ViewGroup.LayoutParams.MATCH_PARENT

                    clDepartDate.visibility = View.VISIBLE
                    clDepartDate.layoutParams = params

                    clDepartDate.setBackgroundResource(R.drawable.bg_black)

                    clReturnDate.visibility = View.GONE
                } else {
                    clReturnDate.visibility = View.VISIBLE
                    clDepartDate.visibility = View.VISIBLE

                    clDepartDate.setBackgroundResource(R.drawable.bg_roundtrip)
                    clReturnDate.setBackgroundResource(R.drawable.bg_roundtrip)

                    params.width = 0
                    clDepartDate.layoutParams = params
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
            }

        })
        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            when (position) {
                0 -> {
                    tab.text = "One-Way"
                }

                else -> {
                    tab.text = "Roundtrip"
                }
            }
        }.attach()
    }
    private fun adapter() {
        val adapter = DiscountAdapter()
        binding.rvDiscount.adapter = adapter

        val tabAdapter = TabTripAdapter(requireActivity())
        binding.viewPager.adapter = tabAdapter

        val ticketPromoAdapter = TicketPromoAdapter()
        binding.rvTicketPromo.adapter = ticketPromoAdapter
    }

    private fun showDatePicker(textView: TextView) {
        // Create the DatePickerFragment
        val datePickerDialog = DatePickerDialog(
            requireContext(),
            { _: DatePicker, year: Int, month: Int, dayOfMonth: Int ->
                // Set the selected date in the TextView
                val calendar = Calendar.getInstance()
                calendar.set(year, month, dayOfMonth)
                val simpleDateFormat = SimpleDateFormat("dd MMMM yyyy", Locale.getDefault())
                val date = simpleDateFormat.format(calendar.time)
                textView.text = date
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