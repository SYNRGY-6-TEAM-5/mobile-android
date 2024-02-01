package com.synrgy.aeroswift.presentation.viewmodel.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.synrgy.domain.Resource
import com.synrgy.domain.body.auth.ValidateOtpBody
import com.synrgy.domain.response.auth.ValidateOtpResponse
import com.synrgy.presentation.usecase.register.ValidateOtpUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class CodeVerifViewModel @Inject constructor(
    private val validateOtpUseCase: ValidateOtpUseCase
): ViewModel() {
    private val _validateOtp = MutableLiveData<ValidateOtpResponse>()
    val validateOtp: LiveData<ValidateOtpResponse> = _validateOtp

    private val _loading: MutableLiveData<Boolean> = MutableLiveData()
    val loading: LiveData<Boolean> = _loading

    private val _error: MutableLiveData<String> = MutableLiveData()
    val error: LiveData<String> = _error

    fun validateOtp(body: ValidateOtpBody) {
        _loading.value = true
        viewModelScope.launch(Dispatchers.IO) {
            when (val response = validateOtpUseCase.invoke(body)) {
                is Resource.Success -> {
                    withContext(Dispatchers.Main) {
                        _validateOtp.value = ValidateOtpResponse(
                            message = response.data?.message ?: "",
                            token = response.data?.token ?: "",
                            success = response.data?.success ?: true
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