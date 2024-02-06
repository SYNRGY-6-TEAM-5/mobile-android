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
import com.synrgy.presentation.usecase.register.ValidateRegisterUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val registerUseCase: RegisterUseCase,
    private val validateRegisterUseCase: ValidateRegisterUseCase
): ViewModel() {
    private val _register: MutableLiveData<RegisterResponse> = MutableLiveData()
    val register: LiveData<RegisterResponse> = _register

    private val _loading: MutableLiveData<Boolean> = MutableLiveData()
    val loading: LiveData<Boolean> = _loading

    private val _error: MutableLiveData<String> = MutableLiveData()
    val error: LiveData<String> = _error

    private val _errors: MutableLiveData<List<ErrorItem?>?> = MutableLiveData()
    val errors: LiveData<List<ErrorItem?>?> = _errors

    private val _localError: MutableLiveData<Boolean> = MutableLiveData()
    val localError: LiveData<Boolean> = _localError

    fun register(user: RegisterBody) {
        _loading.value = true
        _localError.value = false

        viewModelScope.launch(Dispatchers.IO) {
            if (!validateRegisterUseCase.invoke(user.email, user.password)) {
                withContext(Dispatchers.Main) {
                    _localError.value = true
                }
            } else {
                when (val response = registerUseCase.invoke(user)) {
                    is Resource.Success -> {
                        withContext(Dispatchers.Main) {
                            _register.value = RegisterResponse(
                                expiredOTP = response.data?.expiredOTP ?: 0L,
                                otp = response.data?.otp ?: "",
                                success = response.data?.success ?: true
                            )
                        }
                    }
                    is Resource.ErrorRes -> {
                        withContext(Dispatchers.Main) {
                            _errors.value = response.errorRes?.errors ?: emptyList()
                            if (response.errorRes?.errors == null) {
                                _error.value = response.errorRes?.message ?: ""
                            }
                        }
                    }
                    is Resource.ExceptionRes -> {
                        withContext(Dispatchers.Main) {
                            _error.value = response.exceptionRes?.message ?: "Server error"
                        }
                    }
                }
            }

            withContext(Dispatchers.Main) {
                _loading.value = false
            }
        }
    }
}