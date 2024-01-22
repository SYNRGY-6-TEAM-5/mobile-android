package com.synrgy.aeroswift.presentation.viewmodel.forgotpassword

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.synrgy.domain.Resource
import com.synrgy.domain.body.forgotpassword.ForgotPasswordBody
import com.synrgy.domain.response.error.ErrorItem
import com.synrgy.domain.response.forgotpassword.ForgotPasswordResponse
import com.synrgy.presentation.usecase.forgotpassword.ForgotPasswordUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class ForgotPasswordViewModel @Inject constructor(
    private val forgotPasswordUseCase: ForgotPasswordUseCase
): ViewModel() {
    private val _forgotPassword: MutableLiveData<ForgotPasswordResponse> = MutableLiveData()
    val forgotPassword: LiveData<ForgotPasswordResponse> = _forgotPassword

    private val _loading: MutableLiveData<Boolean> = MutableLiveData()
    val loading: LiveData<Boolean> = _loading

    private val _error: MutableLiveData<String> = MutableLiveData()
    val error: LiveData<String> = _error

    private val _errors: MutableLiveData<List<ErrorItem?>?> = MutableLiveData()
    val errors: LiveData<List<ErrorItem?>?> = _errors

    fun forgotPassword(body: ForgotPasswordBody) {
        _loading.value = true
        viewModelScope.launch(Dispatchers.IO) {
            when (val response = forgotPasswordUseCase.invoke(body)) {
                is Resource.Success -> {
                    withContext(Dispatchers.Main) {
                        _loading.value = false
                        _forgotPassword.value = ForgotPasswordResponse(
                            success = response.data?.success ?: true,
                            otp = response.data?.otp ?: "",
                            expiredOTP = response.data?.expiredOTP ?: 0L,
                            email = body.email
                        )
                    }
                }
                is Resource.ErrorRes -> {
                    withContext(Dispatchers.Main) {
                        _loading.value = false
                        _errors.value = response.errorRes?.errors ?: emptyList()
                        if (response.errorRes?.errors == null) {
                            _error.value = response.errorRes?.message ?: ""
                        }
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