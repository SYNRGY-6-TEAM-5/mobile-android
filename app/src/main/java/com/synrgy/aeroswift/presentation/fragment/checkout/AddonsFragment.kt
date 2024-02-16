package com.synrgy.aeroswift.presentation.fragment.checkout

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.synrgy.aeroswift.R
import com.synrgy.aeroswift.databinding.FragmentAddonsBinding
import com.synrgy.aeroswift.dialog.ConfirmationDialog
import com.synrgy.aeroswift.dialog.ExtraProtectionDialog
import com.synrgy.aeroswift.dialog.PriceDetailsDialog
import com.synrgy.aeroswift.models.AddonTravelModels
import com.synrgy.aeroswift.presentation.PaymentActivity
import com.synrgy.aeroswift.presentation.adapter.AddonTravelAdapter
import com.synrgy.aeroswift.presentation.viewmodel.HomeViewModel
import com.synrgy.aeroswift.presentation.viewmodel.checkout.AddonViewModel
import com.synrgy.domain.local.AddonData
import com.synrgy.domain.local.FlightSearch
import com.synrgy.domain.local.PriceDetails
import com.synrgy.presentation.constant.Constant
import com.synrgy.presentation.helper.Helper
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class AddonsFragment : Fragment() {
    private lateinit var binding: FragmentAddonsBinding
    private lateinit var confirmationDialog: ConfirmationDialog
    private lateinit var protectionDialog: ExtraProtectionDialog
    private lateinit var priceDialog: PriceDetailsDialog

    private val homeViewModel: HomeViewModel by viewModels()
    private val addonViewModel: AddonViewModel by viewModels()

    private val baggageAdapter = AddonTravelAdapter()
    private val mealsAdapter = AddonTravelAdapter()

    private val priceList = ArrayList<PriceDetails>()

    private var origin = ""
    private var destination = ""
    private var tripType = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentAddonsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        confirmationDialog = ConfirmationDialog(requireActivity(), ::handleNavigatePayment)
        protectionDialog = ExtraProtectionDialog(requireActivity())
        priceDialog = PriceDetailsDialog(requireActivity())

        homeViewModel.getFlightSearch()
        addonViewModel.getAddons()
        observeViewModel()

        binding.rvBaggage.layoutManager = LinearLayoutManager(requireActivity())
        binding.rvBaggage.adapter = this.baggageAdapter

        binding.rvMeals.layoutManager = LinearLayoutManager(requireActivity())
        binding.rvMeals.adapter = this.mealsAdapter

        binding.infoFullProtection.setOnClickListener {
            protectionDialog.show(
                getString(R.string.full_protection),
                R.drawable.img_protection
            )
        }

        binding.infoIssurance.setOnClickListener {
            protectionDialog.show(
                getString(R.string.baggage_insurance),
                R.drawable.img_issurance
            )
        }

        binding.infoFlightDelay.setOnClickListener {
            protectionDialog.show(
                getString(R.string.flight_delay),
                R.drawable.img_timer
            )
        }

        binding.cbProtection.setOnCheckedChangeListener(::handleCheckedChange)
        binding.cbInsurance.setOnCheckedChangeListener(::handleCheckedChange)
        binding.cbFlightDelay.setOnCheckedChangeListener(::handleCheckedChange)

        binding.tvTotal.setOnClickListener {
            priceDialog.show(
                priceList = priceList,
                isProtection = binding.cbProtection.isChecked,
                isInsurance = binding.cbInsurance.isChecked,
                isDelay = binding.cbFlightDelay.isChecked
            )
        }

        binding.toolbarIcon.setOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
        }

        binding.icBaggage.setOnClickListener {
            findNavController().navigate(R.id.action_addonsFragment_to_addBaggageFragment)
        }
        binding.icMeal.setOnClickListener {
            findNavController().navigate(R.id.action_addonsFragment_to_addMealsFragment)
        }

        binding.btnContinue.setOnClickListener {
            confirmationDialog.show(
                heading = getString(R.string.your_trip_confirmation),
                title = getString(R.string.before_we_proceed),
                description = getString(R.string.trip_confirmation_desc)
            )
        }

        binding.fabScrollUp.visibility = View.GONE

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

    private fun handleNavigatePayment() {
        PaymentActivity.startActivity(requireActivity())
        requireActivity().finish()
    }

    private fun handleCheckedChange(checkbox: CompoundButton, isChecked: Boolean) {
        val color = if (!isChecked) R.color.gray_300 else R.color.base_black
        checkbox.setTextColor(ContextCompat.getColor(requireContext(), color))
    }

    private fun observeViewModel() {
        homeViewModel.flightSearch.observe(viewLifecycleOwner, ::handleFlightSearch)
        addonViewModel.addons.observe(viewLifecycleOwner, ::handleAddons)
    }

    private fun handleFlightSearch(data: FlightSearch) {
        if (Helper.isValidFlightSearch(data)) {
            origin = data.origin!!
            destination = data.destination!!
            tripType = data.tripType!!

            priceList.clear()

            priceList.add(
                PriceDetails(
                    id = Constant.TripType.ONE_WAY.value,
                    oCity = data.oCity!!,
                    dCity = data.dCity!!,
                    adultSeat = data.adultSeat ?: 0,
                    childSeat = data.childSeat ?: 0,
                    infantSeat = data.infantSeat ?: 0,
                    adultPrice = if (data.adultSeat == 0) null else (599000 * data.adultSeat!!).toLong(),
                    childPrice = if (data.childSeat == 0) null else (599000 * data.childSeat!!).toLong(),
                    infantPrice = if (data.infantSeat == 0) null else (599000 * data.infantSeat!!).toLong()
                )
            )

            if (data.tripType == Constant.TripType.ROUNDTRIP.value) {
                priceList.add(
                    PriceDetails(
                        id = Constant.TripType.ROUNDTRIP.value,
                        oCity = data.dCity!!,
                        dCity = data.oCity!!,
                        adultSeat = data.adultSeat ?: 0,
                        childSeat = data.childSeat ?: 0,
                        infantSeat = data.infantSeat ?: 0,
                        adultPrice = if (data.adultSeat == 0) null else (730000 * data.adultSeat!!).toLong(),
                        childPrice = if (data.childSeat == 0) null else (730000 * data.childSeat!!).toLong(),
                        infantPrice = if (data.infantSeat == 0) null else (730000 * data.infantSeat!!).toLong()
                    )
                )
            }
        }
    }

    private fun handleAddons(data: List<AddonData>) {
        val baggageList = ArrayList<AddonTravelModels>()
        val mealsList = ArrayList<AddonTravelModels>()

        var baggagePrice = 0L
        var baggageName = ""
        var baggageCount = ""

        var mealsPrice = 0L
        var mealsName = ""
        var mealsCount = ""

        if (data.isNotEmpty()) {
            val baggageAddons = data
                .filter { it.category == Constant.AddonType.BAGGAGE.value }
                .groupBy { it.passengerId }

            if (baggageAddons.isNotEmpty()) {
                baggageAddons.forEach { (_, value) ->
                    value.forEachIndexed { _, item ->
                        baggagePrice += item.price
                        baggageName += item.userName + "\n"
                        baggageCount += "${item.weight} KG" + "\n"
                    }
                }

                baggageList.add(
                    AddonTravelModels(
                        id = Constant.TripType.ONE_WAY.value,
                        trip = "Depart",
                        price = baggagePrice,
                        origin = origin,
                        destination = destination,
                        name = baggageName,
                        count = baggageCount
                    )
                )

                if (tripType == Constant.TripType.ROUNDTRIP.value) {
                    baggageList.add(
                        AddonTravelModels(
                            id = Constant.TripType.ROUNDTRIP.value,
                            trip = "Return",
                            price = 0,
                            origin = destination,
                            destination = origin
                        )
                    )
                }

                this.baggageAdapter.submitList(baggageList)
                binding.cardBaggageEmpty.visibility = View.GONE
            } else {
                this.baggageAdapter.submitList(emptyList())
                binding.cardBaggageEmpty.visibility = View.VISIBLE
            }

            val mealAddons = data
                .filter { it.category == Constant.AddonType.MEALS.value }
                .groupBy { it.passengerId }

            if (mealAddons.isNotEmpty()) {
                mealAddons.onEachIndexed { index, entry ->
                    entry.value.forEachIndexed { _, item ->
                        mealsPrice += item.price
                    }
                    mealsName += "${entry.value[index].userName}\n"
                    mealsCount += "${entry.value.size} ${if (entry.value.size > 1) "Meals" else "Meal"}\n"
                }

                mealsList.add(
                    AddonTravelModels(
                        id = Constant.TripType.ONE_WAY.value,
                        trip = "Depart",
                        price = mealsPrice,
                        origin = origin,
                        destination = destination,
                        name = mealsName,
                        count = mealsCount
                    )
                )

                if (tripType == Constant.TripType.ROUNDTRIP.value) {
                    mealsList.add(
                        AddonTravelModels(
                            id = Constant.TripType.ROUNDTRIP.value,
                            trip = "Return",
                            price = 0,
                            origin = destination,
                            destination = origin
                        )
                    )
                }

                this.mealsAdapter.submitList(mealsList)
                binding.cardMealEmpty.visibility = View.GONE
            } else {
                this.mealsAdapter.submitList(emptyList())
                binding.cardMealEmpty.visibility = View.VISIBLE
            }
        }
    }
}