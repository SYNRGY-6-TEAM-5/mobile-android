package com.synrgy.aeroswift.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.synrgy.aeroswift.databinding.FragmentOnBoardingBinding
import com.synrgy.aeroswift.presentation.viewmodel.PageViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OnBoardingFragment : Fragment() {
    private val pageViewModel: PageViewModel by viewModels()
    private lateinit var binding: FragmentOnBoardingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        pageViewModel.setIndex(arguments?.getInt(ARG_SECTION_NUMBER) ?: 0)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentOnBoardingBinding.inflate(layoutInflater)
        val view = binding.root

        pageViewModel.index.observe(viewLifecycleOwner, ::handleScrollView)

        return view
    }

    private fun handleScrollView(index: Int) {
        val textList = listOf<String>(
            "Track & find your flight",
            "Manage all your document trip",
            "Easy to schedulling your flight"
        )

        binding.textOnboarding.text = textList[index ?: 0]
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