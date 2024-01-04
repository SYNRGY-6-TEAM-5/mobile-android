package com.synrgy.aeroswift.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.google.android.material.textview.MaterialTextView
import com.synrgy.aeroswift.R
import com.synrgy.aeroswift.presentation.LoginActivity
import com.synrgy.aeroswift.presentation.MainActivity
import com.synrgy.aeroswift.presentation.viewmodel.PageViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OnBoardingFragment : Fragment() {
    private val pageViewModel: PageViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        pageViewModel.setIndex(arguments?.getInt(ARG_SECTION_NUMBER) ?: 1)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_on_boarding, container, false)

        pageViewModel.index.observe(viewLifecycleOwner, ::handleScrollView)

        return root
    }

    private fun handleScrollView(index: Int) {
        val textOnboarding = view?.findViewById<MaterialTextView>(R.id.text_onboarding)

        when (index) {
            0 -> textOnboarding?.text = "Track & find your flight"
            1 -> textOnboarding?.text = "Manage all your document trip"
            2 -> textOnboarding?.text = "Easy to schedulling your flight"
            null -> textOnboarding?.text = "Track & find your flight"
            else -> textOnboarding?.text = "Track & find your flight"
        }
    }

    companion object {
        private const val ARG_SECTION_NUMBER = "section_number"

        @JvmStatic
        fun newInstance(sectionNumber: Int): OnBoardingFragment {
            return OnBoardingFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_SECTION_NUMBER, sectionNumber)
                }
            }
        }
    }
}