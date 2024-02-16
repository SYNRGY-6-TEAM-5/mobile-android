package com.synrgy.aeroswift.presentation.fragment.checkout

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.synrgy.aeroswift.databinding.FragmentCovidBinding

class CovidFragment : Fragment() {
    private lateinit var binding: FragmentCovidBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentCovidBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.toolbarCovid.setNavigationOnClickListener {
            requireActivity().onBackPressed()
        }

        binding.fabScrollUp.visibility = View.GONE

        binding.nsvCovid.viewTreeObserver.addOnScrollChangedListener {
            if (binding.nsvCovid.scrollY <= 200) {
                binding.fabScrollUp.visibility = View.GONE
            } else {
                binding.fabScrollUp.visibility = View.VISIBLE
            }
        }

        binding.fabScrollUp.setOnClickListener {
            binding.nsvCovid.fullScroll(View.FOCUS_UP)
        }
    }
}