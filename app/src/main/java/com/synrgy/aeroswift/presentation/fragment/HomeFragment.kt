package com.synrgy.aeroswift.presentation.fragment

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.synrgy.aeroswift.R
import com.synrgy.aeroswift.databinding.FragmentHomeBinding
import com.synrgy.aeroswift.dialog.PassengersAndCabinClassDialog
import com.synrgy.aeroswift.presentation.FlightDetailsActivity
import com.synrgy.aeroswift.presentation.NotificationActivity
import com.synrgy.aeroswift.presentation.adapter.DiscountAdapter
import com.synrgy.aeroswift.presentation.adapter.TabTripAdapter
import com.synrgy.aeroswift.presentation.adapter.TicketPromoAdapter
import com.synrgy.aeroswift.presentation.viewmodel.HomeViewModel
import com.synrgy.aeroswift.presentation.viewmodel.auth.AuthViewModel
import com.synrgy.domain.local.FlightSearch
import com.synrgy.presentation.constant.Constant
import com.synrgy.presentation.helper.Helper
import dagger.hilt.android.AndroidEntryPoint
import koleton.api.hideSkeleton
import koleton.api.loadSkeleton
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding

    private val viewModel: HomeViewModel by viewModels()
    private val authViewModel: AuthViewModel by viewModels()

    private var departTime = 0L

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter()
        tabController()

        authViewModel.checkAuth()
        authViewModel.getUser()
        observeViewModel()

        binding.clDepartDate.setOnClickListener {
            showDatePicker(binding.tvSelectedDate, true)
        }

        binding.clReturnDate.setOnClickListener {
            if (departTime == 0L) {
                Toast.makeText(requireActivity(), "Select depart date", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }
            showDatePicker(binding.tvSelectedReturnDate, false)
        }

        binding.clPassengerAndCabinClass.setOnClickListener {
            PassengersAndCabinClassDialog(requireActivity(), viewModel).show()
        }

        binding.btnSearchFlight.setOnClickListener { viewModel.setFlightSearch() }

        binding.ivNotification.setOnClickListener {
            NotificationActivity.startActivity(requireActivity())
            requireActivity().finish()
        }
    }

    private fun observeViewModel() {
        authViewModel.loading.observe(viewLifecycleOwner, ::handleLoading)
        authViewModel.name.observe(viewLifecycleOwner, ::handleGetName)
        authViewModel.photo.observe(viewLifecycleOwner, ::handleLoadImage)

        viewModel.totalSeat.observe(viewLifecycleOwner, ::handlePassengerInput)
        viewModel.flightSearch.observe(viewLifecycleOwner, ::handleFlightSearch)
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

                    viewModel.setTripType(Constant.TripType.ONE_WAY.value)
                } else {
                    clReturnDate.visibility = View.VISIBLE
                    clDepartDate.visibility = View.VISIBLE

                    clDepartDate.setBackgroundResource(R.drawable.bg_roundtrip)
                    clReturnDate.setBackgroundResource(R.drawable.bg_roundtrip)

                    params.width = 0
                    clDepartDate.layoutParams = params

                    viewModel.setTripType(Constant.TripType.ROUNDTRIP.value)
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
            }

        })
        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            when (position) {
                0 -> tab.text = "One-Way"
                else -> tab.text = "Roundtrip"
            }
        }.attach()
    }

    private fun adapter() {
        val adapter = DiscountAdapter()
        binding.rvDiscount.adapter = adapter

        val tabAdapter = TabTripAdapter(requireActivity(), viewModel)
        binding.viewPager.adapter = tabAdapter

        val ticketPromoAdapter = TicketPromoAdapter()
        binding.rvTicketPromo.adapter = ticketPromoAdapter
    }

    private fun showDatePicker(textView: TextView, isDepart: Boolean) {
        // Create the DatePickerFragment
        val datePickerDialog = DatePickerDialog(
            requireContext(),
            { _: DatePicker, year: Int, month: Int, dayOfMonth: Int ->
                // Set the selected date in the TextView
                val calendar = Calendar.getInstance()
                calendar.set(year, month, dayOfMonth)

                val simpleDateFormat = SimpleDateFormat("dd MMM yyyy", Locale.US)
                val numberDateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())

                val date = simpleDateFormat.format(calendar.time)
                textView.text = date
                if (isDepart) departTime = calendar.timeInMillis

                val numberDate = numberDateFormat.format(calendar.time)
                if (isDepart) viewModel.setDepDate(numberDate) else viewModel.setRetDate(numberDate)
            },
            // Set the DatePickerDialog to open on the last selected date of the user
            Calendar.getInstance().get(Calendar.YEAR),
            Calendar.getInstance().get(Calendar.MONTH),
            Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
        )
        // Show the DatePickerDialog
        datePickerDialog.datePicker.minDate = if (isDepart) System.currentTimeMillis() else departTime
        datePickerDialog.show()
    }

    private fun handleLoading(loading: Boolean) {
        if (loading) {
            binding.tvName.loadSkeleton()
            binding.ivProfile.loadSkeleton()
        } else {
            binding.tvName.hideSkeleton()
            binding.ivProfile.hideSkeleton()
        }
    }

    private fun handleLoadImage(image: String) {
        if (image.isNotBlank() && image.isNotEmpty()) {
            authViewModel.setPhoto(image)
            Glide
                .with(this)
                .load(image)
                .centerCrop()
                .circleCrop()
                .into(binding.ivProfile)
        }
    }

    private fun handleGetName(name: String) {
        if (name.isNotBlank() && name.isNotEmpty()) {
            authViewModel.setName(name)
            binding.tvName.text = name
        }
    }

    private fun handleFlightSearch(data: FlightSearch) {
        if (!Helper.isValidFlightSearch(data)) {
            Toast.makeText(requireActivity(), "Please fill all field", Toast.LENGTH_SHORT).show()
            return
        }
        FlightDetailsActivity.startActivity(requireActivity())
    }

    private fun handlePassengerInput(totalSeat: Int) {
        if (!totalSeat.equals(null)) {
            val passenger = if (totalSeat > 1) "Passengers" else "Passenger"
            viewModel.ticketClass.observe(viewLifecycleOwner) { selectedClass ->
                if (selectedClass.isNotEmpty() && selectedClass.isNotBlank()) {
                    binding.tvSelectedPassengerAndCabinClass.text = "${totalSeat} ${passenger} - ${selectedClass}"
                }
            }
        }
    }
}