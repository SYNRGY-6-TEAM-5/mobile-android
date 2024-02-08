package com.synrgy.aeroswift.presentation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.OnBackPressedCallback
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import com.atwa.filepicker.core.FilePicker
import com.bumptech.glide.Glide
import com.synrgy.aeroswift.databinding.ActivityEditProfileBinding
import com.synrgy.aeroswift.dialog.ConfirmationDialog
import com.synrgy.aeroswift.dialog.LoadingDialog
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

    private lateinit var loadingDialog: LoadingDialog
    private lateinit var confirmationDialog: ConfirmationDialog

    private val dateFormatter = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
    private var selectedDate = Calendar.getInstance()

    private val filePicker = FilePicker.getInstance(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditProfileBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                handleBack()
            }
        })

        loadingDialog = LoadingDialog(this)
        confirmationDialog = ConfirmationDialog(this) { handleNavigate() }

        authViewModel.checkAuth()
        authViewModel.getUser()

        observeViewModel()

        handleInputChange()

        binding.ivProfile.setOnClickListener { checkPermissions() }
        binding.tvChange.setOnClickListener { checkPermissions() }
        binding.toolbarProfile.setNavigationOnClickListener { onBackPressed() }

        binding.tiBirth.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) Helper.showDatePicker(
                this,
                selectedDate,
                ::updateBirthInput,
                maxDate = System.currentTimeMillis()
            )
        }

        binding.btnSave.setOnClickListener { handleSaveProfile() }
    }

    private fun observeViewModel() {
        authViewModel.loading.observe(this, ::handleLoadingUser)
        authViewModel.name.observe(this, ::handleGetName)
        authViewModel.photo.observe(this, ::handleLoadImage)
        authViewModel.dateBirth.observe(this, ::handleGetDateBirth)
        authViewModel.phoneNumber.observe(this, ::handleGetPhoneNumber)

        editProfileViewModel.errors.observe(this, ::handleErrors)
        editProfileViewModel.error.observe(this, ::handleError)
        editProfileViewModel.loading.observe(this, ::handleLoading)
        editProfileViewModel.editProfile.observe(this, ::handleSuccess)

        uploadProfileImageViewModel.profileImage.observe(this, ::handleUpdateImage)
        uploadProfileImageViewModel.error.observe(this, ::handleError)
        uploadProfileImageViewModel.loading.observe(this, ::handleLoading)
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

    private fun handleLoadingUser(loading: Boolean) {
        if (loading) {
            loadingDialog.startLoadingDialog()
        } else {
            loadingDialog.dismissDialog()
            binding.btnSave.isEnabled = false
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

    private fun handleGetDateBirth(timestamp: Long) {
        val date = Helper.convertTimestampToDate(timestamp)
        binding.tiBirth.setText(date)
    }

    private fun handleGetPhoneNumber(phoneNumber: Long) {
        binding.tiPhone.setText(phoneNumber.toString())
    }

    private fun handleLoadImage(image: String) {
        if (image.isNotBlank() && image.isNotEmpty()) {
            authViewModel.setPhoto(image)

            Glide
                .with(this)
                .load(image)
                .centerCrop()
                .circleCrop()
                .into(binding.ivProfile)
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

    private fun checkPermissions() {
        Helper.requestPermissions(this, doIfGranted = ::showGallery)
    }

    private fun showGallery() {
        filePicker.pickImage { if (it?.file != null) handleUploadImage(it.file!!) }
    }

    private fun handleSaveProfile() {
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

    private fun handleUploadImage(image: File) {
        val imagePart = MultipartBody.Part.createFormData(
            "file", image.name, RequestBody.create(
                Constant.MIME_TYPE_IMAGE.toMediaTypeOrNull(),
                File(image.path).readBytes()
            )
        )

        uploadProfileImageViewModel.uploadProfileImage(image.name, imagePart)
    }

    private fun updateBirthInput() {
        binding.tiBirth.setText(dateFormatter.format(selectedDate.time))
        handleButtonActive()
    }

    private fun handleInputChange() {
        binding.tiFullName.addTextChangedListener { handleButtonActive() }
        binding.tiBirth.addTextChangedListener { handleButtonActive() }
        binding.tiPhone.addTextChangedListener { handleButtonActive() }
    }

    private fun handleButtonActive() {
        binding.btnSave.isEnabled = true
    }

    private fun handleBack() {
        when (binding.btnSave.isEnabled) {
            true -> confirmationDialog.show(
                heading = "Edit Profile Confirmation",
                title = "Changes Not Saved",
                description = "You have modified your profile data. Are you sure you want to go back without saving the changes?"
            )
            false -> handleNavigate()
        }
    }

    private fun handleNavigate() {
        HomeActivity.startProfileFragment(this@EditProfileActivity)
        this@EditProfileActivity.finish()
    }
}