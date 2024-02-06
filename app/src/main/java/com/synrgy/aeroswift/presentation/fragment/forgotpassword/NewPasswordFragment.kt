package com.synrgy.aeroswift.presentation.fragment.forgotpassword

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
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
import com.synrgy.presentation.helper.PasswordStrength
import dagger.hilt.android.AndroidEntryPoint
import com.synrgy.data.helper.Helper as HelperData

@AndroidEntryPoint
class NewPasswordFragment : Fragment() {

    private lateinit var binding: FragmentNewPasswordBinding

    private val editPasswordFpViewModel: EditPasswordFpViewModel by viewModels()

    private lateinit var loadingDialog: LoadingDialog

    private var newPasswordMessage = ""
    private var retypePasswordMessage = ""

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
        handleInputChange()
    }

    private fun observeViewModel() {
        editPasswordFpViewModel.errors.observe(viewLifecycleOwner, ::handleErrors)
        editPasswordFpViewModel.error.observe(viewLifecycleOwner, ::handleError)
        editPasswordFpViewModel.loading.observe(viewLifecycleOwner, ::handleLoading)
        editPasswordFpViewModel.editPassword.observe(viewLifecycleOwner, ::handleSuccess)
        editPasswordFpViewModel.localError.observe(viewLifecycleOwner, ::handleLocalError)
    }

    private fun handleLocalError(error: Boolean) {
        if (error) {
            val newPassword = binding.newPassword.text.toString()
            val retypePassword = binding.retypePassword.text.toString()

            validateNewPassword(newPassword)
            validateRetypePassword(retypePassword)
            validatePassword(newPassword, retypePassword)
            handleSetInputMessage()
        }
    }

    private fun handleError(error: String) {
        if (error.isNotBlank() && error.isNotEmpty()) {
            Helper.showToast(requireActivity(), requireContext(), error, isSuccess = false)
        }
    }

    private fun handleErrors(errors: List<ErrorItem?>?) {
        if (!errors.isNullOrEmpty()) {
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
        newPasswordMessage = ""
        retypePasswordMessage = ""

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

    private fun validateNewPassword(password: String) {
        if (password.isEmpty() && password.isBlank()) {
            newPasswordMessage += "New password is required.\n"
            newPasswordMessage += "New password cannot be blank.\n"
        }
        if (!HelperData.checkPasswordLength(password)) {
            newPasswordMessage += "New password length min 8 character.\n"
        }
        if (!HelperData.containsSpecialCharacter(password)) {
            newPasswordMessage += "New password should contain a special character.\n"
        }
        if (!HelperData.containsAlphanumeric(password)) {
            newPasswordMessage += "New password should contain both letters and numbers.\n"
        }
        if (!HelperData.containsUppercaseLetter(password)) {
            newPasswordMessage += "New password should contain at least one uppercase letter.\n"
        }
    }

    private fun validateRetypePassword(password: String) {
        if (password.isEmpty() && password.isBlank()) {
            retypePasswordMessage += "Retype password is required.\n"
            retypePasswordMessage += "Retype password cannot be blank.\n"
        }
    }

    private fun validatePassword(newPassword: String, retypePassword: String) {
        if (newPassword != retypePassword) {
            retypePasswordMessage += "New Password and Retype Password do not match.\n"
        }
    }

    private fun handleInputChange() {
        binding.newPassword.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                newPasswordMessage = ""
                validateNewPassword(p0.toString())
                calculatePasswordStrength(p0.toString())
            }

            override fun afterTextChanged(p0: Editable?) {
                binding.newTilPassword.error = newPasswordMessage
                binding.newTilPassword.errorIconDrawable = null
            }
        })

        binding.retypePassword.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                retypePasswordMessage = ""
                validateRetypePassword(p0.toString())
                validatePassword(binding.newPassword.text.toString(), p0.toString())
            }

            override fun afterTextChanged(p0: Editable?) {
                binding.retypeTilPassword.error = retypePasswordMessage
                binding.retypeTilPassword.errorIconDrawable = null
            }
        })
    }

    private fun handleSetInputMessage() {
        if ((newPasswordMessage.isNotEmpty() && newPasswordMessage.isNotBlank()) ||
            (retypePasswordMessage.isNotEmpty() && retypePasswordMessage.isNotBlank())) {

            binding.newTilPassword.error = newPasswordMessage.replace(",", "\n")
            binding.newTilPassword.errorIconDrawable = null
            binding.retypeTilPassword.error = retypePasswordMessage.replace(",", "\n")
            binding.retypeTilPassword.errorIconDrawable = null
        }
    }

    private fun calculatePasswordStrength(password: String) {
        val passwordStrength = PasswordStrength.calculate(password)
        binding.tvStrength.text = getString(passwordStrength.message)
        binding.tvStrength.setTextColor(ContextCompat.getColor(requireContext(), passwordStrength.color))
    }
}