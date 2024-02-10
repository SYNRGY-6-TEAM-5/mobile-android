package com.synrgy.aeroswift.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.synrgy.aeroswift.databinding.FragmentTicketBinding
import com.synrgy.aeroswift.dialog.AuthDialog
import com.synrgy.aeroswift.dialog.LoadingDialog
import com.synrgy.aeroswift.presentation.FlightDetailsActivity
import com.synrgy.aeroswift.presentation.SearchActivity
import com.synrgy.aeroswift.presentation.adapter.TicketAdapter
import com.synrgy.aeroswift.presentation.viewmodel.auth.AuthViewModel
import com.synrgy.aeroswift.presentation.viewmodel.ticket.TicketViewModel
import com.synrgy.domain.response.ticket.TicketData
import com.synrgy.presentation.helper.Helper
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

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
    private val authViewModel: AuthViewModel by viewModels()

    private lateinit var loadingDialog: LoadingDialog
    private lateinit var authDialog: AuthDialog

    private var token = ""

    private var departureAirport = ""
    private var arrivalAirport = ""
    private var departureDate = ""
    private var returnDate: String? = null

    private val numberDateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    private val calendar = Calendar.getInstance()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentTicketBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val intent = requireActivity().intent
        departureAirport = intent.getStringExtra(SearchActivity.KEY_DEPARTURE_AIRPORT) ?: ""
        arrivalAirport = intent.getStringExtra(SearchActivity.KEY_ARRIVAL_AIRPORT) ?: ""
        departureDate = intent.getStringExtra(SearchActivity.KEY_DEPARTURE_DATE) ?: ""
        returnDate = intent.getStringExtra(SearchActivity.KEY_RETURN_DATE)

        authDialog = AuthDialog(requireActivity())
        loadingDialog = LoadingDialog(requireActivity())

        authViewModel.getUser()
        authViewModel.checkAuth()

        handleGetTicketData()
        handleSetAdapter()
        observeViewModel()
    }

    private fun handleGetTicketData() {
        ticketViewModel.getDepartTickets(departureAirport, arrivalAirport, getDateString(departureDate))
    }

    private fun observeViewModel() {
        ticketViewModel.loading.observe(viewLifecycleOwner, ::handleLoading)
        ticketViewModel.tickets.observe(viewLifecycleOwner, ::handleGetTickets)
        authViewModel.authentication.observe(viewLifecycleOwner) { token = it }
    }

    private fun handleGetTickets(data: ArrayList<TicketData>) {
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

    private fun handleClickTicket(data: TicketData) {
        if (token.isNotEmpty() && token.isNotBlank()) {
            val bundle = Bundle()
            bundle.putInt(FlightDetailsActivity.KEY_TICKET_ID, data.ticketId ?: 1)

            FlightDetailsActivity.startActivity(requireActivity(), bundle)
        } else {
            authDialog.show()
        }
    }

    private fun getDateString(dateString: String): String {
        calendar.time = Helper.formatStringDate(dateString)!!
        calendar.add(Calendar.DAY_OF_YEAR, (arguments?.getInt(ARG_POSITION) ?: 0) - 1)
        return numberDateFormat.format(calendar.time)
    }
}