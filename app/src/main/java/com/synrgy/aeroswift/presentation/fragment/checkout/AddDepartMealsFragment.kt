package com.synrgy.aeroswift.presentation.fragment.checkout

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.synrgy.aeroswift.R
import com.synrgy.aeroswift.databinding.FragmentAddDepartMealsBinding
import com.synrgy.aeroswift.presentation.adapter.DepartMealsAdapter
import com.synrgy.aeroswift.presentation.viewmodel.auth.AuthViewModel
import com.synrgy.aeroswift.presentation.viewmodel.checkout.AddonViewModel
import com.synrgy.aeroswift.presentation.viewmodel.passenger.PassengerDetailsViewModel
import com.synrgy.domain.local.AddonData
import com.synrgy.domain.local.DepartMeals
import com.synrgy.domain.local.PassengerData
import com.synrgy.presentation.constant.Constant
import com.synrgy.presentation.constant.DepartMealsConstant
import com.synrgy.presentation.helper.Helper
import dagger.hilt.android.AndroidEntryPoint
import koleton.api.hideSkeleton
import koleton.api.loadSkeleton

@AndroidEntryPoint
class AddDepartMealsFragment : Fragment() {
    private lateinit var binding: FragmentAddDepartMealsBinding

    private lateinit var mealsAdapter: DepartMealsAdapter

    private val authViewModel: AuthViewModel by viewModels()
    private val addonViewModel: AddonViewModel by viewModels()
    private val passengerViewModel: PassengerDetailsViewModel by viewModels()

    private var userName: String? = null
    private var userId: String? = null
    private var passengerId: String? = null
    private var addons = mutableListOf<AddonData>()
    private var filteredAddons = mutableListOf<AddonData>()

    private var price = 0L
    private var mealCount = 0

    private var passengerList = mutableListOf<PassengerData>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentAddDepartMealsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        authViewModel.getUser()
        authViewModel.checkAuth()
        passengerViewModel.getPassengers()
        addonViewModel.getAddons()
        observeViewModel()

        binding.toolbarAddMeals.setNavigationOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
        }

        binding.btnSave.setOnClickListener {
            handleSaveAddon()
        }

        binding.tvName.onItemSelectedListener(
            onItemSelected = { position, _ ->
                val passenger = passengerList[position]
                userName = passenger.name
                passengerId = passenger.id

                filteredAddons = addons.filter {
                    it.category == Constant.AddonType.MEALS.value && it.passengerId == passengerId
                }.toMutableList()
                handleSetAdapter(false)
                Log.d("TAGGG", filteredAddons.toString())
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

    private fun handleCheckMeals(data: DepartMeals, isChecked: Boolean) {
        val selectedData = AddonData(
            id = "${data.name}-${passengerId}",
            passengerId = passengerId!!,
            userName = userName!!,
            category = Constant.AddonType.MEALS.value,
            mealName = data.name,
            price = data.price
        )

        if (isChecked) {
            price += data.price
            mealCount += 1
            addons.add(selectedData)
        } else {
            price -= data.price
            if (mealCount > 0) mealCount -= 1
            addons = addons.filter { it.id != selectedData.id }.toMutableList()
        }

        Log.d("TAGGG", addons.toString())

        handleSetPrice(price)
        handleSetMealCount(mealCount)
    }

    private fun handleSaveAddon() {
        if (addons.isNotEmpty()) {
            addonViewModel.deleteAndSaveAllAddons(addons, Constant.AddonType.MEALS.value)
        } else {
            addonViewModel.deleteAddons(Constant.AddonType.MEALS.value)
        }

        requireActivity().supportFragmentManager.popBackStack()
    }

    private fun handleAddons(data: List<AddonData>) {
        if (addons.isEmpty()) {
            addons = data.filter {
                it.category == Constant.AddonType.MEALS.value && it.userId == userId
            }.toMutableList()
        }
        filteredAddons = data.filter {
            it.category == Constant.AddonType.MEALS.value && it.passengerId == passengerId
        }.toMutableList()
    }

    private fun handleSetAdapter(loading: Boolean) {
        if (loading) {
            binding.mealsRecycler.loadSkeleton()
        } else {
            binding.mealsRecycler.hideSkeleton()

            val data = DepartMealsConstant.getData()
            val selectedData = data.filter { item ->
                filteredAddons.any { addon -> addon.price == item.price }
            }
            val selectedIndexes = data.indices.filter { index ->
                data[index] in selectedData
            }.toIntArray()

            this.mealsAdapter = DepartMealsAdapter(selectedIndexes, ::handleCheckMeals)
            binding.mealsRecycler.layoutManager = LinearLayoutManager(activity)
            binding.mealsRecycler.adapter = this.mealsAdapter
            this.mealsAdapter.submitList(data)

            price = selectedData.sumOf { it.price }
            mealCount = selectedData.size

            handleSetPrice(if (selectedData.isNotEmpty()) price else 0L)
            handleSetMealCount(selectedData.size)
        }
    }

    private fun handleSetPrice(price: Long) {
        binding.tvSubtotalPrice.text =
            getString(R.string.depart_baggage_price, Helper.formatPrice(price))
        binding.tvMealPrice.text =
            getString(R.string.depart_baggage_price, Helper.formatPrice(price))
    }

    private fun handleSetMealCount(count: Int) {
        val meals = if (count > 1) "Meals" else "Meal"
        binding.tvMeal.text = "$count $meals"
    }
}

fun Spinner.onItemSelectedListener(
    onItemSelected: (position: Int, item: String) -> Unit,
    onNothingSelected: () -> Unit = {}
) {
    this.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
        override fun onItemSelected(parentView: AdapterView<*>?, selectedItemView: View?, position: Int, id: Long) {
            val selectedItem = parentView?.getItemAtPosition(position).toString()
            onItemSelected.invoke(position, selectedItem)
        }

        override fun onNothingSelected(parentView: AdapterView<*>?) {
            onNothingSelected.invoke()
        }
    }
}