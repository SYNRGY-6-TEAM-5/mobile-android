package com.synrgy.aeroswift.presentation.fragment.accountsetup

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.esafirm.imagepicker.features.ImagePickerConfig
import com.esafirm.imagepicker.features.ImagePickerLauncher
import com.esafirm.imagepicker.features.ImagePickerMode
import com.esafirm.imagepicker.features.registerImagePicker
import com.esafirm.imagepicker.model.Image
import com.synrgy.aeroswift.R
import com.synrgy.aeroswift.databinding.FragmentAccountDetailBinding
import com.synrgy.aeroswift.presentation.AccountSetupActivity
import com.synrgy.aeroswift.presentation.viewmodel.auth.AuthViewModel
import com.synrgy.presentation.helper.Helper
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

@AndroidEntryPoint
class AccountDetailFragment : Fragment() {
    private lateinit var binding: FragmentAccountDetailBinding

    private val authViewModel: AuthViewModel by viewModels()

    private lateinit var imagePickerConfig: ImagePickerConfig
    private lateinit var imagePickerLauncher: ImagePickerLauncher

    private val dateFormatter = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
    private var selectedDate = Calendar.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentAccountDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        handleUpdateProfile()

        imagePickerConfig = ImagePickerConfig {
            language = "en"
            mode = ImagePickerMode.SINGLE
            theme = R.style.Theme_AeroSwift_ImagePicker
            isFolderMode = true
        }
        imagePickerLauncher = registerImagePicker { results: List<Image> ->
            results.forEach { handleUpdateImage(it.uri.toString()) }
        }

        binding.tvSkipAccountDetail.setOnClickListener { handleNavigate() }

        binding.ivProfileSetup.setOnClickListener { checkPermissions() }
        binding.tvUploadImage.setOnClickListener { checkPermissions() }

        binding.accountDetailTiBirth.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) Helper.showDatePicker(requireContext(), selectedDate, ::updateBirthInput)
        }

        binding.btnConfirmCodeAccountDetail.setOnClickListener { handleConfirmProfile() }
    }

    private fun handleNavigate() {
        findNavController().navigate(R.id.action_accountDetailFragment_to_setupEmailSuccessFragment)
    }

    private fun handleUpdateImage(imageUri: String) {
        handleLoadImage(imageUri)
        authViewModel.setPhoto(imageUri)
    }

    private fun checkPermissions() {
        Helper.requestPermissions(requireActivity(), doIfGranted = ::showGallery)
    }

    private fun showGallery() {
        imagePickerLauncher.launch(imagePickerConfig)
    }

    private fun handleConfirmProfile() {
        val name = binding.accountDetailTiName.text.toString()
        val birthDate = binding.accountDetailTiBirth.text.toString()
        val phone = binding.accountDetailTiPhone.text.toString()

        if (name.isEmpty() && name.isBlank()) {
            binding.accountDetailTilName.error = "Required"
        }

        if (birthDate.isEmpty() && birthDate.isBlank()) {
            binding.accountDetailTilBirth.error = "Required"
        }

        if (phone.isEmpty() && phone.isBlank()) {
            binding.accountDetailTilPhone.error = "Required"
            return
        }

        authViewModel.setName(name)
        handleNavigate()
    }

    private fun handleUpdateProfile() {
        val bundle = requireActivity().intent.extras
        val name = bundle?.getString(AccountSetupActivity.KEY_NAME_SETUP)
        val photo = bundle?.getString(AccountSetupActivity.KEY_PHOTO_SETUP)

        binding.accountDetailTiName.setText(name)
        if (photo != null) {
            handleLoadImage(photo)
        }
    }

    private fun handleLoadImage(imageUri: String) {
        Glide
            .with(this)
            .load(imageUri)
            .centerCrop()
            .circleCrop()
            .into(binding.ivProfilePlaceholder)
    }

    private fun updateBirthInput() {
        binding.accountDetailTiBirth.setText(dateFormatter.format(selectedDate.time))
    }
}