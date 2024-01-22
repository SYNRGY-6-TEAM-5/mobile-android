package com.synrgy.aeroswift.presentation.fragment.forgotpassword

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.synrgy.aeroswift.databinding.FragmentForgotPassBinding
import com.synrgy.aeroswift.dialog.LoadingDialog
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class ForgotPassFragment : Fragment() {

    private lateinit var binding: FragmentForgotPassBinding

    private val coroutineScope: CoroutineScope = CoroutineScope(Job() + Dispatchers.Main)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentForgotPassBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val loadingDialog = LoadingDialog(requireActivity())

        binding.btnSendCode.setOnClickListener {
            loadingDialog.startLoadingDialog()

            coroutineScope.launch {
                delay(1000)
                loadingDialog.dismissDialog()
                sendCode()
            }
        }

//        val bundle = requireActivity().intent.extras
//        binding.radioEmail.hint = Helper.maskEmail(bundle?.getString(ForgotPassDialog.KEY_EMAIL_RECOVERY) ?: "test@gmail.com")

        binding.toolbarForgotPass.setOnClickListener { requireActivity().onBackPressed() }
    }

    private fun sendCode() {
//        findNavController().navigate(R.id.action_forgotPassFragment_to_verificationCodePassFragment)
    }
}