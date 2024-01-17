package com.synrgy.aeroswift.presentation.fragment.checkout

import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ClickableSpan
import android.text.style.ForegroundColorSpan
import android.text.style.UnderlineSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.synrgy.aeroswift.R
import com.synrgy.aeroswift.databinding.FragmentPassengerDomBinding

class PassengerDomFragment : Fragment() {
    private lateinit var binding: FragmentPassengerDomBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentPassengerDomBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        handleTextSpan()
    }

    private fun handleTextSpan() {
        val covidText: Spannable =
            SpannableString(resources.getString(R.string.covid19_details))
        val startIndex = covidText.indexOf("Covid-19 infopage")
        val endIndex = startIndex + "Covid-19 infopage".length

        covidText.setSpan(
            ForegroundColorSpan(ContextCompat.getColor(requireContext(), R.color.primary_500)),
            startIndex,
            endIndex,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )

        covidText.setSpan(
            UnderlineSpan(),
            startIndex,
            endIndex,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )

        val clickableSpan = object : ClickableSpan() {
            override fun onClick(widget: View) {
            }
        }
        covidText.setSpan(
            clickableSpan,
            startIndex,
            endIndex,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )

        binding.tvCovid19Details.text = covidText
        binding.tvCovid19Details.movementMethod = android.text.method.LinkMovementMethod.getInstance()
    }
}