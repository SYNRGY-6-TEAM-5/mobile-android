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
import com.synrgy.aeroswift.dialog.LoadingDialog
import com.synrgy.aeroswift.presentation.AccountSetupActivity
import com.synrgy.aeroswift.presentation.viewmodel.auth.AuthViewModel
import com.synrgy.aeroswift.presentation.viewmodel.user.EditProfileViewModel
import com.synrgy.aeroswift.presentation.viewmodel.user.UploadProfileImageViewModel
import com.synrgy.domain.body.user.EditProfileBody
import com.synrgy.domain.response.auth.UploadProfileImageResponse
import com.synrgy.domain.response.error.ErrorItem
import com.synrgy.domain.response.user.EditProfileResponse
import com.synrgy.presentation.constant.Constant
import com.synrgy.presentation.helper.Helper
import dagger.hilt.android.AndroidEntryPoint
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

@AndroidEntryPoint
class AccountDetailFragment : Fragment() {
    private lateinit var binding: FragmentAccountDetailBinding

    private val authViewModel: AuthViewModel by viewModels()
    private val editProfileViewModel: EditProfileViewModel by viewModels()
    private val uploadProfileImageViewModel: UploadProfileImageViewModel by viewModels()

    private lateinit var imagePickerConfig: ImagePickerConfig
    private lateinit var imagePickerLauncher: ImagePickerLauncher

    private lateinit var loadingDialog: LoadingDialog

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

        loadingDialog = LoadingDialog(requireActivity())

        imagePickerConfig = ImagePickerConfig {
            language = "en"
            mode = ImagePickerMode.SINGLE
            theme = R.style.Theme_AeroSwift_ImagePicker
            isFolderMode = true
        }
        imagePickerLauncher = registerImagePicker { results: List<Image> ->
            results.forEach { handleUploadImage(it) }
        }

        handleGetProfile()
        observeViewModel()

        binding.tvSkipAccountDetail.setOnClickListener { handleNavigate() }

        binding.ivProfileSetup.setOnClickListener { checkPermissions() }
        binding.tvUploadImage.setOnClickListener { checkPermissions() }

        binding.accountDetailTiBirth.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) Helper.showDatePicker(requireContext(), selectedDate, ::updateBirthInput)
        }

        binding.btnConfirmCodeAccountDetail.setOnClickListener { handleConfirmProfile() }
    }

    private fun observeViewModel() {
        editProfileViewModel.errors.observe(viewLifecycleOwner, ::handleErrors)
        editProfileViewModel.error.observe(viewLifecycleOwner, ::handleError)
        editProfileViewModel.loading.observe(viewLifecycleOwner, ::handleLoading)
        editProfileViewModel.editProfile.observe(viewLifecycleOwner, ::handleSuccess)

        uploadProfileImageViewModel.profileImage.observe(viewLifecycleOwner, ::handleUpdateImage)
        uploadProfileImageViewModel.error.observe(viewLifecycleOwner, ::handleError)
        uploadProfileImageViewModel.loading.observe(viewLifecycleOwner, ::handleLoading)
    }

    private fun handleError(error: String) {
        if (error.isNotBlank() && error.isNotEmpty()) {
            Helper.showToast(requireActivity(), requireContext(), error, isSuccess = false)
        }
    }

    private fun handleErrors(errors: List<ErrorItem?>?) {
        if (!errors.isNullOrEmpty()) {
            var fullNameMessage = ""
            var dobMessage = ""
            var phoneNumberMessage = ""

            for (error in errors) {
                when (error?.field) {
                    "fullName" -> fullNameMessage += error.defaultMessage + "\n"
                    "dob" -> dobMessage += error.defaultMessage + "\n"
                    "phoneNumber" -> phoneNumberMessage += error.defaultMessage + "\n"
                }
            }

            binding.accountDetailTilName.error = fullNameMessage.replace(",", "\n")
            binding.accountDetailTilBirth.error = dobMessage.replace(",", "\n")
            binding.accountDetailTilPhone.error = phoneNumberMessage.replace(",", "\n")
        }
    }

    private fun handleLoading(loading: Boolean) {
        if (loading) {
            loadingDialog.startLoadingDialog()
        } else {
            loadingDialog.dismissDialog()
        }
    }

    private fun handleSuccess(response: EditProfileResponse) {
        if (response.message.isNotEmpty() &&
            response.message.isNotBlank()) {

            loadingDialog.dismissDialog()
            authViewModel.setName(binding.accountDetailTiName.text.toString())
            Helper.showToast(requireActivity(), requireContext(), response.message, isSuccess = true)
            handleNavigate()
        }
    }

    private fun handleUpdateImage(response: UploadProfileImageResponse) {
        if (!response.message.isNullOrEmpty() &&
            !response.message.isNullOrBlank() &&
            !response.urlImage.isNullOrEmpty() &&
            !response.urlImage.isNullOrBlank() &&
            response.success != false
        ) {

            handleLoadImage(response.urlImage!!)
            authViewModel.setPhoto(response.urlImage!!)
            Helper.showToast(requireActivity(), requireContext(), response.message!!, isSuccess = true)
        }
    }

    private fun handleNavigate() {
        findNavController().navigate(R.id.action_accountDetailFragment_to_setupEmailSuccessFragment)
    }

    private fun checkPermissions() {
        Helper.requestPermissions(requireActivity(), doIfGranted = ::showGallery)
    }

    private fun handleUploadImage(image: Image) {
        val imagePart = MultipartBody.Part.createFormData(
            "file", image.name, RequestBody.create(
                Constant.MIME_TYPE_IMAGE.toMediaTypeOrNull(),
                File(image.path).readBytes()
            )
        )
        uploadProfileImageViewModel.uploadProfileImage(image.name, imagePart)
    }

    private fun showGallery() {
        imagePickerLauncher.launch(imagePickerConfig)
    }

    private fun handleConfirmProfile() {
        val name = binding.accountDetailTiName.text.toString()
        var birthDate = binding.accountDetailTiBirth.text.toString()
        val phone = binding.accountDetailTiPhone.text.toString()
        var phoneNumber = 8L

        if (phone.isNotEmpty() && phone.isNotBlank()) {
            phoneNumber = phone.toLong()
        }

        if (Helper.isValidDateFormat(birthDate)) {
            birthDate = Helper.convertDateFormat(birthDate)
        }

        binding.accountDetailTilName.error = null
        binding.accountDetailTilBirth.error = null
        binding.accountDetailTilPhone.error = null

        editProfileViewModel.editProfile(
            EditProfileBody(
                fullName = name,
                dob = birthDate,
                phoneNumber = phoneNumber
            )
        )
    }

    private fun handleGetProfile() {
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