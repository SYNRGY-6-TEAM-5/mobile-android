package com.synrgy.aeroswift.presentation.fragment.checkout

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.synrgy.aeroswift.R
import com.synrgy.aeroswift.databinding.FragmentAddDepartMealsBinding
import com.synrgy.aeroswift.presentation.adapter.DepartMealsAdapter
import com.synrgy.aeroswift.presentation.viewmodel.auth.AuthViewModel
import com.synrgy.aeroswift.presentation.viewmodel.checkout.AddonViewModel
import com.synrgy.domain.local.AddonData
import com.synrgy.domain.local.DepartMeals
import com.synrgy.presentation.constant.Constant
import com.synrgy.presentation.constant.DepartMealsConstant
import com.synrgy.presentation.helper.Helper
import dagger.hilt.android.AndroidEntryPoint
import koleton.api.hideSkeleton
import koleton.api.loadSkeleton

@AndroidEntryPoint
class AddDepartMealsFragment : Fragment() {
    private lateinit var binding: FragmentAddDepartMealsBinding
    private lateinit var adapter: DepartMealsAdapter

    private val addonViewModel: AddonViewModel by viewModels()
    private val authViewModel: AuthViewModel by viewModels()

    private var userName: String? = null
    private var userId: String? = null
    private var addons = mutableListOf<AddonData>()

    private var price = 0L
    private var mealCount = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentAddDepartMealsBinding.inflate(inflater, container, false)
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        authViewModel.getUser()
        authViewModel.checkAuth()
        addonViewModel.getAddons()
        observeViewModel()

        binding.toolbarAddMeals.setNavigationOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
        }

        binding.btnSave.setOnClickListener {
            handleSaveAddon()
        }
    }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun observeViewModel() {
        authViewModel.name.observe(viewLifecycleOwner) {
            userName = it
            binding.tvName.text = it
        }
        authViewModel.userId.observe(viewLifecycleOwner) { userId = it }
        addonViewModel.addons.observe(viewLifecycleOwner, ::handleAddons)
        addonViewModel.loading.observe(viewLifecycleOwner, ::handleSetAdapter)
    }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun handleCheckMeals(data: DepartMeals, isChecked: Boolean) {
        val selectedData = AddonData(
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
            addons = addons.filter { it.price != selectedData.price }.toMutableList()
        }

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
        if (data.isNotEmpty()) {
            addons = data.filter {
                it.category == Constant.AddonType.MEALS.value && it.userId == userId
            }.toMutableList()
        }
    }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun handleSetAdapter(loading: Boolean) {
        if (loading) {
            binding.mealsRecycler.loadSkeleton()
        } else {
            binding.mealsRecycler.hideSkeleton()

            val data = DepartMealsConstant.getData()
            val selectedData = data.filter { item ->
                addons.any { addon -> addon.price == item.price }
            }
            val selectedIndexes = data.indices.filter { index ->
                data[index] in selectedData
            }.toIntArray()

            this.adapter = DepartMealsAdapter(selectedIndexes, ::handleCheckMeals)
            binding.mealsRecycler.layoutManager = LinearLayoutManager(activity)
            binding.mealsRecycler.adapter = this.adapter
            this.adapter.submitList(data)

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