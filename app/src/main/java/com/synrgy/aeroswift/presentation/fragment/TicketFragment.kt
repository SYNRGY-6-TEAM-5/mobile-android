package com.synrgy.aeroswift.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.synrgy.aeroswift.databinding.FragmentTicketBinding
import com.synrgy.aeroswift.dialog.LoadingDialog
import com.synrgy.aeroswift.presentation.FlightBookingActivity
import com.synrgy.aeroswift.presentation.FlightDetailsActivity
import com.synrgy.aeroswift.presentation.HomeActivity
import com.synrgy.aeroswift.presentation.SearchActivity
import com.synrgy.aeroswift.presentation.adapter.TicketAdapter
import com.synrgy.aeroswift.presentation.viewmodel.ticket.TicketViewModel
import com.synrgy.domain.response.ticket.TicketData
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TicketFragment : Fragment() {
    companion object {
        private const val ARG_POSITION = "position"

        fun newInstance(position: Int): TicketFragment {
            val fragment = TicketFragment()
            val args = Bundle()
            args.putInt(ARG_POSITION, position)
            fragment.arguments = args
            return fragment
        }
    }

    private lateinit var binding: FragmentTicketBinding

    private val ticketAdapter = TicketAdapter(::handleClickTicket)

    private val ticketViewModel: TicketViewModel by viewModels()

    private lateinit var loadingDialog: LoadingDialog

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentTicketBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                HomeActivity.startActivity(requireActivity())
                requireActivity().finish()
            }
        })

        loadingDialog = LoadingDialog(requireActivity())

        handleGetTicketData()
        handleSetAdapter()
        observeViewModel()
    }

    private fun handleGetTicketData() {
        val intent = requireActivity().intent
        val departureAirport = intent.getStringExtra(SearchActivity.KEY_DEPARTURE_AIRPORT) ?: ""
        val arrivalAirport = intent.getStringExtra(SearchActivity.KEY_ARRIVAL_AIRPORT) ?: ""
        val departureDate = intent.getStringExtra(SearchActivity.KEY_DEPARTURE_DATE) ?: ""
        val returnDate = intent.getStringExtra(SearchActivity.KEY_RETURN_DATE)

        ticketViewModel.getTickets("", "", "")
        if (returnDate != null) ticketViewModel.getTickets("", "", "")
    }

    private fun observeViewModel() {
        ticketViewModel.loading.observe(viewLifecycleOwner, ::handleLoading)
        ticketViewModel.tickets.observe(viewLifecycleOwner, ::handleGetTickets)
    }

    private fun handleGetTickets(data: ArrayList<TicketData>) {
        if (data.isEmpty()) {
            FlightBookingActivity.startActivity(requireActivity())
            requireActivity().finish()
        }

        ticketAdapter.submitList(data)
        binding.tvSearchResult.text = "“${data.size} Result”"
    }

    private fun handleLoading(loading: Boolean) {
        if (loading) loadingDialog.startLoadingDialog() else loadingDialog.dismissDialog()
    }

    private fun handleSetAdapter() {
        binding.rvTicket.layoutManager = LinearLayoutManager(requireActivity())
        binding.rvTicket.adapter = ticketAdapter
    }

    private fun handleClickTicket() {
        FlightDetailsActivity.startActivity(requireActivity())
    }
}