package com.synrgy.aeroswift.presentation.fragment.accountsetup

import android.os.Bundle
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.synrgy.aeroswift.databinding.FragmentSetupEmailSuccessBinding
import com.synrgy.aeroswift.presentation.AuthActivity

class SetupEmailSuccessFragment : Fragment() {
    private lateinit var binding: FragmentSetupEmailSuccessBinding
    private lateinit var countDownTimer: CountDownTimer

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentSetupEmailSuccessBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        handleTimer()
    }

    private fun handleTimer() {
        val totalTimeInMillis: Long = 4 * 1000

        countDownTimer = object : CountDownTimer(totalTimeInMillis, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                val minutes = millisUntilFinished / 1000 / 60
                val seconds = (millisUntilFinished / 1000) % 60
                val timeString = String.format("%d:%02d", minutes, seconds)

                binding.tvTimerAccountSetup.text = timeString
            }

            override fun onFinish() { handleNavigateToLogin() }
        }

        countDownTimer.start()
    }

    private fun handleNavigateToLogin() {
        AuthActivity.startActivity(requireActivity())
        requireActivity().finish()
    }

    override fun onDestroy() {
        super.onDestroy()
        countDownTimer.cancel()
    }
}