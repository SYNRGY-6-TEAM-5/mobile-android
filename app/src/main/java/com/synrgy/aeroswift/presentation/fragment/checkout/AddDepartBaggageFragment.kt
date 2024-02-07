package com.synrgy.aeroswift.presentation.fragment.checkout

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.synrgy.aeroswift.R
import com.synrgy.aeroswift.databinding.FragmentAddDepartBaggageBinding
import com.synrgy.aeroswift.presentation.adapter.DepartBaggageAdapter
import com.synrgy.aeroswift.presentation.viewmodel.auth.AuthViewModel
import com.synrgy.aeroswift.presentation.viewmodel.checkout.AddonViewModel
import com.synrgy.aeroswift.presentation.viewmodel.passenger.PassengerDetailsViewModel
import com.synrgy.domain.local.AddonData
import com.synrgy.domain.local.DepartBaggage
import com.synrgy.domain.local.PassengerData
import com.synrgy.presentation.constant.Constant
import com.synrgy.presentation.constant.DepartBaggageConstant
import com.synrgy.presentation.helper.Helper
import dagger.hilt.android.AndroidEntryPoint
import koleton.api.hideSkeleton
import koleton.api.loadSkeleton


@AndroidEntryPoint
class AddDepartBaggageFragment : Fragment() {
    private lateinit var binding: FragmentAddDepartBaggageBinding
    private lateinit var adapter: DepartBaggageAdapter

    private val addonViewModel: AddonViewModel by viewModels()
    private val authViewModel: AuthViewModel by viewModels()
    private val passengerViewModel: PassengerDetailsViewModel by viewModels()

    private var userName: String? = null
    private var userId: String? = null
    private var passengerId: String? = null
    private var addons = mutableListOf<AddonData>()
    private var filteredAddon: AddonData? = null
    private var price = 0L

    private var passengerList = mutableListOf<PassengerData>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentAddDepartBaggageBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        authViewModel.getUser()
        authViewModel.checkAuth()
        passengerViewModel.getPassengers()
        addonViewModel.getAddons()
        observeViewModel()

        binding.toolbarAddBaggage.setNavigationOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
        }

        binding.btnSave.setOnClickListener { handleSaveAddon() }

        binding.tvName.onItemSelectedListener(
            onItemSelected = { position, _ ->
                val passenger = passengerList[position]
                userName = passenger.name
                passengerId = passenger.id

                filteredAddon = addons.findLast {
                    it.category == Constant.AddonType.BAGGAGE.value && it.passengerId == passengerId
                }
                handleSetAdapter(false)
            },
        )
    }

    private fun observeViewModel() {
        authViewModel.userId.observe(viewLifecycleOwner) { userId = it }
        addonViewModel.addons.observe(viewLifecycleOwner, ::handleAddons)
        addonViewModel.loading.observe(viewLifecycleOwner, ::handleSetAdapter)
        passengerViewModel.passengers.observe(viewLifecycleOwner, ::handleSetSpinner)
    }

    private fun handleSetSpinner(data: List<PassengerData>) {
        if (data.isNotEmpty()) {
            passengerList = data.toMutableList()
            val spinner = binding.tvName
            val adapter = ArrayAdapter(
                requireActivity(),
                R.layout.item_dropdown_spinner,
                data.map { it.name }
            )
            spinner.adapter = adapter
        }
    }

    private fun handleClickBaggage(data: DepartBaggage) {
        val selectedData = AddonData(
            passengerId = passengerId!!,
            userName = userName!!,
            category = Constant.AddonType.BAGGAGE.value,
            weight = data.weight,
            price = data.price
        )

        if (!data.selected) {
            price = data.price
            addons = addons.filter { it.passengerId != selectedData.passengerId }.toMutableList()
            addons.add(selectedData)
        } else {
            price = 0L
            addons = addons.filter { it.weight != selectedData.weight }.toMutableList()
        }

        handleSetPrice(price)
    }

    private fun handleSaveAddon() {
        if (addons.isNotEmpty()) {
            addonViewModel.deleteAndSaveAllAddons(addons, Constant.AddonType.BAGGAGE.value)
        } else {
            addonViewModel.deleteAddons(Constant.AddonType.BAGGAGE.value)
        }

        requireActivity().supportFragmentManager.popBackStack()
    }

    private fun handleAddons(data: List<AddonData>) {
        if (addons.isEmpty()) {
            addons = data.filter {
                it.category == Constant.AddonType.BAGGAGE.value && it.userId == userId
            }.toMutableList()
        }
        filteredAddon = data.findLast {
            it.category == Constant.AddonType.BAGGAGE.value && it.passengerId == passengerId
        }
    }

    private fun handleSetAdapter(loading: Boolean) {
        if (loading) {
            binding.baggageRecycler.loadSkeleton()
        } else {
            binding.baggageRecycler.hideSkeleton()

            val data = DepartBaggageConstant.getData()
            val selectedData = data.find { item -> filteredAddon?.weight == item.weight }
            val selectedPosition = data.indexOfFirst { it.weight == selectedData?.weight }

            this.adapter = DepartBaggageAdapter(selectedPosition, ::handleClickBaggage)
            binding.baggageRecycler.layoutManager = LinearLayoutManager(activity)
            binding.baggageRecycler.adapter = this.adapter
            this.adapter.submitList(data)

            handleSetPrice(if (filteredAddon != null) filteredAddon!!.price else 0L)
        }
    }

    private fun handleSetPrice(price: Long) {
        binding.tvSubtotalPrice.text =
            getString(R.string.depart_baggage_price, Helper.formatPrice(price))
        binding.tvMealPrice.text =
            getString(R.string.depart_baggage_price, Helper.formatPrice(price))
    }
}