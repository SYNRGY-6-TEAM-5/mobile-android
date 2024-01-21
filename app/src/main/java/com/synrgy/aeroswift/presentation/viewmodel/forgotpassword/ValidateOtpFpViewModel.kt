package com.synrgy.aeroswift.presentation.viewmodel.forgotpassword

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.synrgy.domain.Resource
import com.synrgy.domain.body.forgotpassword.ValidateOtpFpBody
import com.synrgy.domain.response.forgotpassword.ValidateOtpFpResponse
import com.synrgy.presentation.usecase.forgotpassword.ValidateOtpFpUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class ValidateOtpFpViewModel @Inject constructor(
    private val validateOtpFpUseCase: ValidateOtpFpUseCase
): ViewModel() {
    private val _validateOtp: MutableLiveData<ValidateOtpFpResponse> = MutableLiveData()
    val validateOtp: LiveData<ValidateOtpFpResponse> = _validateOtp

    private val _loading: MutableLiveData<Boolean> = MutableLiveData()
    val loading: LiveData<Boolean> = _loading

    private val _error: MutableLiveData<String> = MutableLiveData()
    val error: LiveData<String> = _error

    fun validateOtp(body: ValidateOtpFpBody) {
        _loading.value = true
        viewModelScope.launch(Dispatchers.IO) {
            when (val response = validateOtpFpUseCase.invoke(body)) {
                is Resource.Success -> {
                    withContext(Dispatchers.Main) {
                        _loading.value = false
                        _validateOtp.value = ValidateOtpFpResponse(
                            status = response.data?.status ?: true,
                            token = response.data?.token ?: "",
                            message = response.data?.message ?: ""
                        )
                    }
                }
                is Resource.ErrorRes -> {
                    withContext(Dispatchers.Main) {
                        _loading.value = false
                        _error.value = response.errorRes?.message ?: ""
                    }
                }
                is Resource.ExceptionRes -> {
                    withContext(Dispatchers.Main) {
                        _loading.value = false
                        _error.value = response.exceptionRes?.message ?: ""
                    }
                }
            }
        }
    }
}