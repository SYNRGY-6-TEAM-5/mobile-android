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
import com.synrgy.aeroswift.presentation.viewmodel.airport.AirportListViewModel
import com.synrgy.domain.response.airport.AirportData
import com.synrgy.domain.response.airport.AirportResponse
import java.util.Locale

class AirportListDialog(
    private val activity: Activity,
    private val viewModel: AirportListViewModel,
    private val viewLifecycleOwner: LifecycleOwner
) {
    private lateinit var dialog: BottomSheetDialog
    private lateinit var binding: DialogAirportListBinding

    private lateinit var airportList: List<AirportData?>

    private val allAirportListAdapter = AirportListAdapter { handleAirportClick(it) }
    private val recentAirportListAdapter = AirportListAdapter { handleAirportClick(it) }

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

        binding.tvClearAl.setOnClickListener {
            this.recentAirportListAdapter.submitList(ArrayList<AirportData>())
        }

        binding.ivClose.setOnClickListener { dialog.dismiss() }
    }

    private fun observeViewModel() {
        viewModel.isDest.observe(viewLifecycleOwner) { isDest = it }
        viewModel.airport.observe(viewLifecycleOwner, ::handleGetAirportList)
        viewModel.recentAirport.observe(viewLifecycleOwner, ::handleGetRecentAirport)
    }

    private fun setupFullHeight(bottomSheet: View) {
        val layoutParams = bottomSheet.layoutParams
        layoutParams.height = WindowManager.LayoutParams.MATCH_PARENT
        bottomSheet.layoutParams = layoutParams
    }

    private fun handleSearchAirport(text: String) {
        val filteredList = ArrayList<AirportData>()

        for (item in airportList) {
            if (item?.airPortName?.lowercase()?.contains(text.lowercase(Locale.getDefault())) == true) {
                filteredList.add(item)
            }
        }

        this.allAirportListAdapter.submitList(filteredList)
    }

    private fun handleAirportClick(data: AirportData) {
        if (!isDest) viewModel.setOrigin(data) else viewModel.setDestination(data)
        viewModel.addRecentAirport(data)
        dialog.dismiss()
    }

    private fun handleGetAirportList(response: AirportResponse) {
        if (response.status == true) {
            this.airportList = response.data!!
            this.allAirportListAdapter.submitList(this.airportList)
            this.recentAirportListAdapter.submitList(this.airportList.takeLast(3))
        }
    }

    private fun handleGetRecentAirport(data: MutableSet<String>?) {
//        if (data != null) {
//            this.recentAirportListAdapter.submitList(this.airportList.filter {
//                data.contains(it!!.id.toString())
//            })
//        }
    }
}