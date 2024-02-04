package com.synrgy.aeroswift.presentation.fragment.checkout

import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ClickableSpan
import android.text.style.ForegroundColorSpan
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.synrgy.aeroswift.R
import com.synrgy.aeroswift.databinding.FragmentPassengerIntBinding
import com.synrgy.aeroswift.dialog.LoadingDialog
import com.synrgy.aeroswift.presentation.adapter.InputDocAdapter
import com.synrgy.aeroswift.presentation.viewmodel.passenger.PassengerDetailsViewModel
import com.synrgy.domain.local.DocumentData
import com.synrgy.domain.local.PassengerData
import com.synrgy.presentation.helper.Helper
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

@AndroidEntryPoint
class PassengerIntFragment : Fragment() {
    companion object {
        const val KEY_PASSENGER_CATEGORY = "key_passenger_category"
    }

    private lateinit var binding: FragmentPassengerIntBinding

    private lateinit var loadingDialog: LoadingDialog

    private val dateFormatter = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
    private var selectedDate = Calendar.getInstance()

    private val viewModel: PassengerDetailsViewModel by viewModels()

    private val inputDocAdapter = InputDocAdapter()

    private val docList = mutableListOf(DocumentData())

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentPassengerIntBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loadingDialog = LoadingDialog(requireActivity())

        observeViewModel()

        handleSetAdapter()
        handleTextSpan()

        binding.tiBirth.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) Helper.showDatePicker(requireContext(), selectedDate, ::updateBirthInput)
        }

        binding.btnSave.setOnClickListener { handleSavePassenger() }
        binding.toolbarPassengerInt.setNavigationOnClickListener { handleNavigate() }
        binding.chipAddOtherDoc.setOnClickListener {
            docList.add(DocumentData())
            handleSetAdapter()
        }
    }

    private fun observeViewModel() {
        viewModel.loading.observe(viewLifecycleOwner, ::handleLoading)
        viewModel.success.observe(viewLifecycleOwner, ::handleSuccess)
    }

    private fun handleSetAdapter() {
        binding.rvInputDoc.layoutManager = LinearLayoutManager(requireActivity())
        binding.rvInputDoc.adapter = this.inputDocAdapter
        this.inputDocAdapter.submitList(docList)
    }

    private fun handleTextSpan() {
        val covidText: Spannable =
            SpannableString(resources.getString(R.string.covid19_details))
        val startIndex = covidText.indexOf("Covid-19 infopage")
        val endIndex = startIndex + "Covid-19 infopage".length

        val clickableSpan = object : ClickableSpan() {
            override fun onClick(widget: View) {
                val action = PassengerIntFragmentDirections
                    .actionPassengerIntFragmentToCovidFragment()
                findNavController().navigate(action)
            }
        }
        covidText.setSpan(
            clickableSpan,
            startIndex,
            endIndex,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )

        covidText.setSpan(
            ForegroundColorSpan(ContextCompat.getColor(requireContext(), R.color.primary_500)),
            startIndex,
            endIndex,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )

        binding.tvCovid19Details.text = covidText
        binding.tvCovid19Details.movementMethod = android.text.method.LinkMovementMethod.getInstance()
    }

    private fun updateBirthInput() {
        binding.tiBirth.setText(dateFormatter.format(selectedDate.time))
    }

    private fun handleNavigate() {
        requireActivity().supportFragmentManager.popBackStack()
    }

    private fun handleLoading(loading: Boolean) {
        if (loading) loadingDialog.startLoadingDialog() else loadingDialog.dismissDialog()
    }

    private fun handleSuccess(success: Boolean) {
        if (success) {
            loadingDialog.dismissDialog()
            Helper.showToast(requireActivity(), requireContext(), "Save data success!", true)
            handleNavigate()
        }
    }

    private fun handleSavePassenger() {
        val nik = binding.tiNik.text.toString()
        val name = binding.tiName.text.toString()
        val dob = binding.tiBirth.text.toString()
        val category = arguments?.getString(KEY_PASSENGER_CATEGORY).toString()

        val data = PassengerData(
            id = Date().time.toString(),
            nik = nik,
            name = name,
            dob = dob,
            category = category
        )

        viewModel.addPassenger(data)
    }
}