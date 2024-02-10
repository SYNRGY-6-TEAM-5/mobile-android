package com.synrgy.aeroswift.presentation.viewmodel.user

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.synrgy.domain.Resource
import com.synrgy.domain.response.auth.UploadProfileImageResponse
import com.synrgy.presentation.usecase.login.GetTokenUseCase
import com.synrgy.presentation.usecase.user.UploadProfileImageUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MultipartBody
import javax.inject.Inject

@HiltViewModel
class UploadProfileImageViewModel @Inject constructor(
    private val uploadProfileImageUseCase: UploadProfileImageUseCase,
    private val getTokenUseCase: GetTokenUseCase
): ViewModel() {
    private val _profileImage: MutableLiveData<UploadProfileImageResponse> = MutableLiveData()
    val profileImage: LiveData<UploadProfileImageResponse> = _profileImage

    private val _loading: MutableLiveData<Boolean> = MutableLiveData()
    val loading: LiveData<Boolean> = _loading

    private val _error: MutableLiveData<String> = MutableLiveData()
    val error: LiveData<String> = _error

    fun uploadProfileImage(
        name: String,
        file: MultipartBody.Part
    ) {
        _loading.value = true
        viewModelScope.launch(Dispatchers.IO) {
            when (val response = uploadProfileImageUseCase.invoke(
                getTokenUseCase.invoke().first() ?: "",
                name,
                file
            )) {
                is Resource.Success -> {
                    withContext(Dispatchers.Main) {
                        _profileImage.value = UploadProfileImageResponse(
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