package com.synrgy.aeroswift.dialog

import android.app.Activity
import android.view.View
import android.view.WindowManager
import android.widget.SearchView
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.synrgy.aeroswift.databinding.DialogAirportListBinding
import com.synrgy.aeroswift.presentation.adapter.AirportListAdapter
import com.synrgy.aeroswift.presentation.viewmodel.HomeViewModel
import com.synrgy.aeroswift.presentation.viewmodel.airport.AirportListViewModel
import com.synrgy.domain.response.airport.AirportData
import com.synrgy.domain.response.airport.AirportResponse

class AirportListDialog(
    private val activity: Activity,
    private val viewModel: AirportListViewModel,
    private val homeViewModel: HomeViewModel,
    private val viewLifecycleOwner: LifecycleOwner
) {
    private lateinit var dialog: BottomSheetDialog
    private lateinit var binding: DialogAirportListBinding

    private var airportList: List<AirportData?> = emptyList()

    private val allAirportListAdapter = AirportListAdapter(::handleAirportClick)
    private val recentAirportListAdapter = AirportListAdapter(::handleAirportClick)

    private var isDest = false

    fun show() {
        dialog = BottomSheetDialog(activity)
        binding = DialogAirportListBinding.inflate(activity.layoutInflater)
        val view = binding.root

        dialog.setContentView(view)
        dialog.setCancelable(true)
        dialog.show()

        val parentLayout =
            dialog.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet)

        parentLayout?.let {
            val behaviour = BottomSheetBehavior.from(it)
            setupFullHeight(it)
            behaviour.state = BottomSheetBehavior.STATE_EXPANDED
        }

        viewModel.getRecentAirport()
        observeViewModel()

        binding.airportListRecycler.layoutManager = LinearLayoutManager(activity)
        binding.recentAirportListRecycler.layoutManager = LinearLayoutManager(activity)

        binding.recentAirportListRecycler.adapter = this.recentAirportListAdapter
        binding.airportListRecycler.adapter = this.allAirportListAdapter

        binding.svAirportList.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                handleSearchAirport(newText)
                return false
            }
        })

        binding.tvClearAl.setOnClickListener { viewModel.clearRecentAirport() }
        binding.ivClose.setOnClickListener { dialog.dismiss() }

        binding.nestedScrollView.viewTreeObserver.addOnScrollChangedListener {
            if (binding.nestedScrollView.scrollY <= 200) {
                binding.fabScrollUp.visibility = View.GONE
            } else {
                binding.fabScrollUp.visibility = View.VISIBLE
            }
        }

        binding.fabScrollUp.setOnClickListener {
            binding.nestedScrollView.fullScroll(View.FOCUS_UP)
        }
    }

    private fun observeViewModel() {
        viewModel.isDest.observe(viewLifecycleOwner) { isDest = it }
        viewModel.airport.observe(viewLifecycleOwner, ::handleGetAirportList)
        viewModel.recentAirport.observe(viewLifecycleOwner, ::handleGetRecentAirport)
        viewModel.error.observe(viewLifecycleOwner, ::handleError)
    }

    private fun setupFullHeight(bottomSheet: View) {
        val layoutParams = bottomSheet.layoutParams
        layoutParams.height = WindowManager.LayoutParams.MATCH_PARENT
        bottomSheet.layoutParams = layoutParams
    }

    private fun handleError(error: Boolean) {
        binding.tvNoAirport.visibility = if (error) View.VISIBLE else View.GONE
    }

    private fun handleSearchAirport(text: String) {
        val filteredList = ArrayList<AirportData>()

        for (item in airportList) {
            if (item?.cityName?.lowercase()?.contains(text.lowercase()) == true ||
                item?.countryName?.lowercase()?.contains(text.lowercase()) == true ||
                item?.airPortName?.lowercase()?.contains(text.lowercase()) == true ||
                item?.iataCode?.lowercase()?.contains(text.lowercase()) == true ||
                item?.cityIataCode?.lowercase()?.contains(text.lowercase()) == true) {

                filteredList.add(item)
            }
        }

        this.allAirportListAdapter.submitList(filteredList)
    }

    private fun handleAirportClick(data: AirportData) {
        if (!isDest) {
            homeViewModel.setOrigin(data.iataCode!!)
            homeViewModel.setOCity(data.cityName!!)
        } else {
            homeViewModel.setDestination(data.iataCode!!)
            homeViewModel.setDCity(data.cityName!!)
        }
        viewModel.addRecentAirport(data)
        dialog.dismiss()
    }

    private fun handleGetAirportList(response: AirportResponse) {
        binding.tvNoAirport.visibility = if (response.data.isNullOrEmpty()) View.VISIBLE else View.GONE
        this.airportList = response.data ?: emptyList()
        this.allAirportListAdapter.submitList(this.airportList)
    }

    private fun handleGetRecentAirport(data: List<AirportData>) {
        binding.tvNoRecent.visibility = if (data.isEmpty()) View.VISIBLE else View.GONE
        this.recentAirportListAdapter.submitList(data.take(3))
    }
}