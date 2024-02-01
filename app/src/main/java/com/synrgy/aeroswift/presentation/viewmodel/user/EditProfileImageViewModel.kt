package com.synrgy.aeroswift.presentation.viewmodel.user

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.synrgy.domain.Resource
import com.synrgy.domain.response.user.EditProfileImageResponse
import com.synrgy.presentation.usecase.login.GetTokenUseCase
import com.synrgy.presentation.usecase.user.EditProfileImageUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MultipartBody
import javax.inject.Inject

@HiltViewModel
class EditProfileImageViewModel @Inject constructor(
    private val editProfileImageUseCase: EditProfileImageUseCase,
    private val getTokenUseCase: GetTokenUseCase
): ViewModel() {
    private val _profileImage: MutableLiveData<EditProfileImageResponse> = MutableLiveData()
    val profileImage: LiveData<EditProfileImageResponse> = _profileImage

    private val _loading: MutableLiveData<Boolean> = MutableLiveData()
    val loading: LiveData<Boolean> = _loading

    private val _error: MutableLiveData<String> = MutableLiveData()
    val error: LiveData<String> = _error

    fun editProfileImage(file: MultipartBody.Part) {
        _loading.value = true
        viewModelScope.launch(Dispatchers.IO) {
            when (val response = editProfileImageUseCase.invoke(
                getTokenUseCase.invoke().first() ?: "",
                file
            )) {
                is Resource.Success -> {
                    withContext(Dispatchers.Main) {
                        _profileImage.value = EditProfileImageResponse(
                            message = response.data?.message ?: "",
                            success = response.data?.success ?: true,
                            urlImage = response.data?.urlImage ?: ""
                        )
                    }
                }
                is Resource.ErrorRes -> {
                    withContext(Dispatchers.Main) {
                        _error.value = response.errorRes?.message ?: ""
                    }
                }
                is Resource.ExceptionRes -> {
                    withContext(Dispatchers.Main) {
                        _error.value = response.exceptionRes?.message ?: "Server error"
                    }
                }
            }

            withContext(Dispatchers.Main) {
                _loading.value = false
            }
        }
    }
}