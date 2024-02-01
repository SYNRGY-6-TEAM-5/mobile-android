package com.synrgy.aeroswift.presentation.fragment.checkout

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.synrgy.aeroswift.R
import com.synrgy.aeroswift.databinding.FragmentAddonsBinding
import com.synrgy.aeroswift.dialog.ExtraProtectionDialog
import com.synrgy.aeroswift.dialog.PriceDetailsDialog
import com.synrgy.aeroswift.dialog.TripConfirmationDialog
import com.synrgy.aeroswift.presentation.viewmodel.HomeViewModel
import com.synrgy.aeroswift.presentation.viewmodel.auth.AuthViewModel
import com.synrgy.domain.local.FlightSearch
import com.synrgy.domain.local.PriceDetails
import com.synrgy.presentation.constant.Constant
import com.synrgy.presentation.helper.Helper
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class AddonsFragment : Fragment() {
    private lateinit var binding: FragmentAddonsBinding
    private lateinit var confirmationDialog: TripConfirmationDialog
    private lateinit var protectionDialog: ExtraProtectionDialog
    private lateinit var priceDialog: PriceDetailsDialog

    private val homeViewModel: HomeViewModel by viewModels()

    private val priceList = ArrayList<PriceDetails>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentAddonsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        confirmationDialog = TripConfirmationDialog(requireActivity())
        protectionDialog = ExtraProtectionDialog(requireActivity())
        priceDialog = PriceDetailsDialog(requireActivity())

        homeViewModel.getFlightSearch()
        observeViewModel()

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

        binding.toolbarIcon.setOnClickListener { requireActivity().onBackPressed() }

        binding.icBaggage.setOnClickListener {
            findNavController().navigate(R.id.action_addonsFragment_to_addBaggageFragment)
        }
        binding.icMeal.setOnClickListener {
            findNavController().navigate(R.id.action_addonsFragment_to_addMealsFragment)
        }

        binding.btnContinue.setOnClickListener { confirmationDialog.show() }
    }

    private fun handleCheckedChange(checkbox: CompoundButton, isChecked: Boolean) {
        val color = if (!isChecked) R.color.gray_300 else R.color.base_black
        checkbox.setTextColor(ContextCompat.getColor(requireContext(), color))
    }

    private fun observeViewModel() {
        homeViewModel.flightSearch.observe(viewLifecycleOwner, ::handleFlightSearch)
    }

    private fun handleFlightSearch(data: FlightSearch) {
        if (Helper.isValidFlightSearch(data)) {
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
}