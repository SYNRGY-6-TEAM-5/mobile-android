package com.synrgy.aeroswift.presentation.viewmodel.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.synrgy.domain.Resource
import com.synrgy.domain.body.auth.RegisterBody
import com.synrgy.domain.response.auth.RegisterResponse
import com.synrgy.domain.response.error.ErrorItem
import com.synrgy.presentation.usecase.register.RegisterUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val registerUseCase: RegisterUseCase
): ViewModel() {
    private val _register: MutableLiveData<RegisterResponse> = MutableLiveData()
    val register: LiveData<RegisterResponse> = _register

    private val _loading: MutableLiveData<Boolean> = MutableLiveData()
    val loading: LiveData<Boolean> = _loading

    private val _error: MutableLiveData<String> = MutableLiveData()
    val error: LiveData<String> = _error

    private val _errors: MutableLiveData<List<ErrorItem?>?> = MutableLiveData()
    val errors: LiveData<List<ErrorItem?>?> = _errors

    fun register(user: RegisterBody) {
        _loading.value = true
        viewModelScope.launch(Dispatchers.IO) {
            when (val response = registerUseCase.invoke(user)) {
                is Resource.Success -> {
                    withContext(Dispatchers.Main) {
                        _loading.value = false
                        _register.value = RegisterResponse(
                            expiredOTP = response.data?.expiredOTP ?: 0L,
                            otp = response.data?.otp ?: "",
                            success = response.data?.success ?: true
                        )
                    }
                }
                is Resource.ErrorRes -> {
                    withContext(Dispatchers.Main) {
                        _loading.value = false
                        _error.value = response.errorRes?.message ?: ""
                        _errors.value = response.errorRes?.errors ?: emptyList()
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