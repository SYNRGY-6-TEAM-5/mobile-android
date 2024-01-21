package com.synrgy.aeroswift.presentation.fragment.forgotpassword

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.synrgy.aeroswift.R
import com.synrgy.aeroswift.databinding.FragmentNewPasswordBinding
import com.synrgy.aeroswift.dialog.LoadingDialog
import com.synrgy.aeroswift.presentation.viewmodel.forgotpassword.EditPasswordFpViewModel
import com.synrgy.domain.body.forgotpassword.EditPasswordFpBody
import com.synrgy.domain.response.error.ErrorItem
import com.synrgy.domain.response.forgotpassword.EditPasswordFpResponse
import com.synrgy.presentation.helper.Helper
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NewPasswordFragment : Fragment() {

    private lateinit var binding: FragmentNewPasswordBinding

    private val editPasswordFpViewModel: EditPasswordFpViewModel by viewModels()

    private lateinit var loadingDialog: LoadingDialog

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNewPasswordBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loadingDialog = LoadingDialog(requireActivity())

        observeViewModel()

        binding.btnChangePassword.setOnClickListener { changePass() }
    }

    private fun observeViewModel() {
        editPasswordFpViewModel.errors.observe(viewLifecycleOwner, ::handleErrors)
        editPasswordFpViewModel.error.observe(viewLifecycleOwner, ::handleError)
        editPasswordFpViewModel.loading.observe(viewLifecycleOwner, ::handleLoading)
        editPasswordFpViewModel.editPassword.observe(viewLifecycleOwner, ::handleSuccess)
    }

    private fun handleError(error: String) {
        if (error.isNotBlank() && error.isNotEmpty()) {
            Helper.showToast(requireActivity(), requireContext(), error, isSuccess = false)
        }
    }

    private fun handleErrors(errors: List<ErrorItem?>?) {
        if (!errors.isNullOrEmpty()) {
            var newPasswordMessage = ""
            var retypePasswordMessage = ""

            for (error in errors) {
                when (error?.field) {
                    "newPassword" -> newPasswordMessage += error.defaultMessage + "\n"
                    "retypePassword" -> retypePasswordMessage += error.defaultMessage + "\n"
                }
            }

            binding.newTilPassword.error = newPasswordMessage.replace(",", "\n")
            binding.newTilPassword.errorIconDrawable = null
            binding.retypeTilPassword.error = retypePasswordMessage.replace(",", "\n")
            binding.retypeTilPassword.errorIconDrawable = null
        }
    }

    private fun handleLoading(loading: Boolean) {
        if (loading) {
            loadingDialog.startLoadingDialog()
        } else {
            loadingDialog.dismissDialog()
        }
    }

    private fun handleSuccess(response: EditPasswordFpResponse) {
        if (!response.message.isNullOrEmpty() &&
            !response.message.isNullOrBlank() &&
            response.status == true
        ) {

            Helper.showToast(requireActivity(), requireContext(), response.message!!, isSuccess = true)
            handleNavigate()
        }
    }

    private fun changePass() {
        val newPassword = binding.newPassword.text.toString()
        val retypePassword = binding.retypePassword.text.toString()

        binding.newTilPassword.error = null
        binding.retypeTilPassword.error = null

        editPasswordFpViewModel.editPassword(
            EditPasswordFpBody(
                newPassword = newPassword,
                retypePassword = retypePassword
            )
        )
    }

    private fun handleNavigate() {
        findNavController().navigate(R.id.action_newPasswordFragment_to_doneResetPassFragment)
    }
}