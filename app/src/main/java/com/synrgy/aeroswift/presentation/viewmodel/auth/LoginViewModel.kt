package com.synrgy.aeroswift.presentation.viewmodel.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.synrgy.domain.Resource
import com.synrgy.domain.body.auth.LoginBody
import com.synrgy.domain.response.auth.LoginResponse
import com.synrgy.domain.response.error.ErrorItem
import com.synrgy.presentation.usecase.login.LoginUseCase
import com.synrgy.presentation.usecase.login.ValidateLoginUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
    private val validateLoginUseCase: ValidateLoginUseCase
): ViewModel() {
    private val _login: MutableLiveData<LoginResponse> = MutableLiveData()
    val login: LiveData<LoginResponse> = _login

    private val _loading: MutableLiveData<Boolean> = MutableLiveData()
    val loading: LiveData<Boolean> = _loading

    private val _error: MutableLiveData<String> = MutableLiveData()
    val error: LiveData<String> = _error

    private val _errors: MutableLiveData<List<ErrorItem?>?> = MutableLiveData()
    val errors: LiveData<List<ErrorItem?>?> = _errors

    private val _localError: MutableLiveData<Boolean> = MutableLiveData()
    val localError: LiveData<Boolean> = _localError

    fun login(user: LoginBody) {
        _loading.value = true
        _localError.value = false

        viewModelScope.launch(Dispatchers.IO) {
            if (!validateLoginUseCase(user.emailAddress, user.password)) {
                withContext(Dispatchers.Main) {
                    _localError.value = true
                }
            } else {
                when (val response = loginUseCase.invoke(user)) {
                    is Resource.Success -> {
                        withContext(Dispatchers.Main) {
                            _login.value = LoginResponse(
                                email = response.data?.email ?: "",
                                roles = response.data?.roles ?: emptyList(),
                                token = response.data?.token ?: "",
                                type = response.data?.type ?: "",
                                message = "Login success!"
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