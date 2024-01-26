package com.synrgy.aeroswift.presentation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.OnBackPressedCallback
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.esafirm.imagepicker.features.ImagePickerConfig
import com.esafirm.imagepicker.features.ImagePickerLauncher
import com.esafirm.imagepicker.features.ImagePickerMode
import com.esafirm.imagepicker.features.registerImagePicker
import com.esafirm.imagepicker.model.Image
import com.synrgy.aeroswift.R
import com.synrgy.aeroswift.databinding.ActivityEditProfileBinding
import com.synrgy.aeroswift.dialog.LoadingDialog
import com.synrgy.aeroswift.presentation.viewmodel.auth.AuthViewModel
import com.synrgy.aeroswift.presentation.viewmodel.user.EditProfileImageViewModel
import com.synrgy.aeroswift.presentation.viewmodel.user.EditProfileViewModel
import com.synrgy.aeroswift.presentation.viewmodel.user.UploadProfileImageViewModel
import com.synrgy.domain.body.user.EditProfileBody
import com.synrgy.domain.response.auth.UploadProfileImageResponse
import com.synrgy.domain.response.error.ErrorItem
import com.synrgy.domain.response.user.EditProfileImageResponse
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
class EditProfileActivity : AppCompatActivity() {
    companion object {
        fun startActivity(context: Context) {
            context.startActivity(Intent(context, EditProfileActivity::class.java))
        }
    }

    private lateinit var binding: ActivityEditProfileBinding

    private val authViewModel: AuthViewModel by viewModels()
    private val editProfileViewModel: EditProfileViewModel by viewModels()
    private val uploadProfileImageViewModel: UploadProfileImageViewModel by viewModels()
    private val editProfileImageViewModel: EditProfileImageViewModel by viewModels()

    private lateinit var loadingDialog: LoadingDialog

    private lateinit var imagePickerConfig: ImagePickerConfig
    private lateinit var imagePickerLauncher: ImagePickerLauncher

    private val dateFormatter = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
    private var selectedDate = Calendar.getInstance()

    private var isEditImage = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditProfileBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        loadingDialog = LoadingDialog(this)

        imagePickerConfig = ImagePickerConfig {
            language = "en"
            mode = ImagePickerMode.SINGLE
            theme = R.style.Theme_AeroSwift_ImagePicker
            isFolderMode = true
        }
        imagePickerLauncher = registerImagePicker { results: List<Image> ->
            results.forEach { handleUploadImage(it) }
        }

        authViewModel.checkAuth()
        authViewModel.getUser()

        observeViewModel()

        binding.ivProfile.setOnClickListener { checkPermissions() }
        binding.tvUploadImage.setOnClickListener { checkPermissions() }
        binding.toolbarProfile.setNavigationOnClickListener { handleNavigate() }

        binding.tiBirth.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) Helper.showDatePicker(this, selectedDate, ::updateBirthInput)
        }

        binding.btnConfirm.setOnClickListener { handleConfirmProfile() }

        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                handleNavigate()
            }
        })
    }

    private fun observeViewModel() {
        authViewModel.loading.observe(this, ::handleLoading)
        authViewModel.name.observe(this, ::handleGetName)
        authViewModel.photo.observe(this, ::handleLoadImage)

        editProfileViewModel.errors.observe(this, ::handleErrors)
        editProfileViewModel.error.observe(this, ::handleError)
        editProfileViewModel.loading.observe(this, ::handleLoading)
        editProfileViewModel.editProfile.observe(this, ::handleSuccess)

        uploadProfileImageViewModel.profileImage.observe(this, ::handleUpdateImage)
        uploadProfileImageViewModel.error.observe(this, ::handleError)
        uploadProfileImageViewModel.loading.observe(this, ::handleLoading)

        editProfileImageViewModel.profileImage.observe(this, ::handleUpdateImage)
        editProfileImageViewModel.error.observe(this, ::handleError)
        editProfileImageViewModel.loading.observe(this, ::handleLoading)
    }

    private fun handleError(error: String) {
        if (error.isNotBlank() && error.isNotEmpty()) {
            Helper.showToast(this, this, error, isSuccess = false)
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

            binding.tilFullName.error = fullNameMessage.replace(",", "\n")
            binding.tilBirth.error = dobMessage.replace(",", "\n")
            binding.tilPhone.error = phoneNumberMessage.replace(",", "\n")
        }
    }

    private fun handleSuccess(response: EditProfileResponse) {
        if (response.message.isNotEmpty() &&
            response.message.isNotBlank()) {

            authViewModel.setName(binding.tiFullName.text.toString())
            Helper.showToast(this, this, response.message, isSuccess = true)
            handleNavigate()
        }
    }

    private fun handleLoading(loading: Boolean) {
        if (loading) {
            loadingDialog.startLoadingDialog()
        } else {
            loadingDialog.dismissDialog()
        }
    }

    private fun handleGetName(name: String) {
        authViewModel.setName(name)
        binding.tiFullName.setText(name)
    }

    private fun handleLoadImage(image: String) {
        if (image.isNotBlank() && image.isNotEmpty()) {
            isEditImage = true
            authViewModel.setPhoto(image)

            Glide
                .with(this)
                .load(image)
                .centerCrop()
                .circleCrop()
                .into(binding.ivProfilePlaceholder)
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
            Helper.showToast(this, this, response.message!!, isSuccess = true)
        }
    }

    private fun handleUpdateImage(response: EditProfileImageResponse) {
        if (!response.message.isNullOrEmpty() &&
            !response.message.isNullOrBlank() &&
            !response.urlImage.isNullOrEmpty() &&
            !response.urlImage.isNullOrBlank() &&
            response.success != false
        ) {

            handleLoadImage(response.urlImage!!)
            authViewModel.setPhoto(response.urlImage!!)
            Helper.showToast(this, this, response.message!!, isSuccess = true)
        }
    }

    private fun checkPermissions() {
        Helper.requestPermissions(this, doIfGranted = ::showGallery)
    }

    private fun showGallery() {
        imagePickerLauncher.launch(imagePickerConfig)
    }

    private fun handleConfirmProfile() {
        val name = binding.tiFullName.text.toString()
        var birthDate = binding.tiBirth.text.toString()
        val phone = binding.tiPhone.text.toString()
        var phoneNumber = 8L

        if (phone.isNotEmpty() && phone.isNotBlank()) {
            phoneNumber = phone.toLong()
        }

        if (Helper.isValidDateFormat(birthDate)) {
            birthDate = Helper.convertDateFormat(birthDate)
        }

        binding.tilFullName.error = null
        binding.tilBirth.error = null
        binding.tilPhone.error = null

        editProfileViewModel.editProfile(
            EditProfileBody(
                fullName = name,
                dob = birthDate,
                phoneNumber = phoneNumber
            )
        )
    }

    private fun handleUploadImage(image: Image) {
        val imagePart = MultipartBody.Part.createFormData(
            "file", image.name, RequestBody.create(
                Constant.MIME_TYPE_IMAGE.toMediaTypeOrNull(),
                File(image.path).readBytes()
            )
        )

        if (isEditImage) {
            editProfileImageViewModel.editProfileImage(imagePart)
        } else {
            uploadProfileImageViewModel.uploadProfileImage(image.name, imagePart)
        }
    }

    private fun updateBirthInput() {
        binding.tiBirth.setText(dateFormatter.format(selectedDate.time))
    }

    private fun handleNavigate() {
        val bundle = Bundle()
        bundle.putInt(HomeActivity.KEY_FRAGMENT_INDEX, 2)

        HomeActivity.startActivity(this, bundle)
        this.finish()
    }
}