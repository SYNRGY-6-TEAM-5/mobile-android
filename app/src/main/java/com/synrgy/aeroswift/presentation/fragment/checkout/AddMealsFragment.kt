package com.synrgy.aeroswift.presentation.fragment.checkout

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.synrgy.aeroswift.R
import com.synrgy.aeroswift.databinding.FragmentAddMealsBinding
import com.synrgy.aeroswift.models.AddonModels
import com.synrgy.aeroswift.presentation.adapter.AddonAdapter
import com.synrgy.aeroswift.presentation.viewmodel.HomeViewModel
import com.synrgy.aeroswift.presentation.viewmodel.checkout.AddonViewModel
import com.synrgy.domain.local.AddonData
import com.synrgy.domain.local.FlightSearch
import com.synrgy.presentation.constant.Constant
import dagger.hilt.android.AndroidEntryPoint
import koleton.api.hideSkeleton
import koleton.api.loadSkeleton

@AndroidEntryPoint
class AddMealsFragment : Fragment() {
    private lateinit var binding: FragmentAddMealsBinding

    private val homeViewModel: HomeViewModel by viewModels()
    private val addonViewModel: AddonViewModel by viewModels()

    private val adapter = AddonAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentAddMealsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        homeViewModel.getFlightSearch()
        addonViewModel.getAddons()
        observeViewModel()

        binding.rvMeals.layoutManager = LinearLayoutManager(requireActivity())
        binding.rvMeals.adapter = this.adapter

        binding.toolbarAddMeals.setNavigationOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
        }

        binding.btnDone.setOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
        }

        binding.tvDepartSelect.setOnClickListener {
            findNavController().navigate(R.id.action_addMealsFragment_to_addDepartMealsFragment)
        }
    }

    private fun observeViewModel() {
        homeViewModel.flightSearch.observe(viewLifecycleOwner, ::handleFlightSearch)
        homeViewModel.loading.observe(viewLifecycleOwner, ::handleLoading)

        addonViewModel.addons.observe(viewLifecycleOwner, ::handleGetAddons)
    }

    private fun handleFlightSearch(data: FlightSearch) {
        if (data.origin != null && data.destination != null) {
            binding.tvDepartOrigin.text = data.origin
            binding.tvDepartDest.text = data.destination

            binding.tvReturnOrigin.text = data.destination
            binding.tvReturnDest.text = data.origin

            if (data.tripType == Constant.TripType.ROUNDTRIP.value) {
                binding.tvReturn.visibility = View.VISIBLE
                binding.layoutReturn.visibility = View.VISIBLE
            }
        }
    }

    private fun handleLoading(loading: Boolean) {
        if (loading) binding.layout.loadSkeleton() else binding.layout.hideSkeleton()
    }

    private fun handleGetAddons(addons: List<AddonData>) {
        if (addons.isNotEmpty()) {
            val filteredAddons = addons
                .filter { it.category == Constant.AddonType.MEALS.value }
                .groupBy { it.passengerId }

            if (filteredAddons.isNotEmpty()) binding.tvDepartSelect.text = "Change"

            val list = mutableListOf<AddonModels>()

            filteredAddons.forEach { (_, value) ->
                val meals = if (value.size > 1) "Meals" else "Meal"
                list.add(
                    AddonModels(
                        id = value[0].passengerId,
                        name = value[0].userName,
                        count = "${value.size} $meals"
                    )
                )
            }

            this.adapter.submitList(list)
        } else {
            this.adapter.submitList(emptyList())
        }
    }
}