package com.synrgy.aeroswift.presentation.fragment.checkout

import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ClickableSpan
import android.text.style.ForegroundColorSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.synrgy.aeroswift.R
import com.synrgy.aeroswift.databinding.FragmentPassengerIntBinding
import com.synrgy.presentation.helper.Helper
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class PassengerIntFragment : Fragment() {
    private lateinit var binding: FragmentPassengerIntBinding

    private val dateFormatter = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
    private var selectedDate = Calendar.getInstance()

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

        handleTextSpan()

        binding.tiBirth.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) Helper.showDatePicker(requireContext(), selectedDate, ::updateBirthInput)
        }

        binding.btnSave.setOnClickListener { handleNavigate() }
        binding.toolbarPassengerInt.setNavigationOnClickListener { handleNavigate() }
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
        requireActivity().onBackPressed()
    }
}