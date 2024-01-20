package com.synrgy.aeroswift.presentation.viewmodel.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.synrgy.domain.Resource
import com.synrgy.domain.body.LoginBody
import com.synrgy.domain.response.ErrorItem
import com.synrgy.presentation.usecase.login.LoginUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase
): ViewModel() {
    private val _login: MutableLiveData<String> = MutableLiveData()
    val login: LiveData<String> = _login

    private val _authentication = MutableLiveData<String>()
    val authentication: LiveData<String> = _authentication

    private val _loading: MutableLiveData<Boolean> = MutableLiveData()
    val loading: LiveData<Boolean> = _loading

    private val _error: MutableLiveData<String> = MutableLiveData()
    val error: LiveData<String> = _error

    private val _errors: MutableLiveData<List<ErrorItem?>?> = MutableLiveData()
    val errors: LiveData<List<ErrorItem?>?> = _errors

    fun login(user: LoginBody) {
        _loading.value = true
        viewModelScope.launch(Dispatchers.IO) {
            when (val response = loginUseCase.invoke(user)) {
                is Resource.Success -> {
                    withContext(Dispatchers.Main) {
                        _loading.value = false
                        _authentication.value = response.data?.token ?: ""
                        _login.value = "Login success!"
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