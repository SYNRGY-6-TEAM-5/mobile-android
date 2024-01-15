package com.synrgy.aeroswift.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.synrgy.domain.LoginBody
import com.synrgy.presentation.usecase.login.LoginUseCase
import com.synrgy.presentation.usecase.login.LoginValidateInputUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
    private val loginValidateInputUseCase: LoginValidateInputUseCase
): ViewModel() {
    private val _login: MutableLiveData<String> = MutableLiveData()
    val login: LiveData<String> = _login

    private val _authentication = MutableLiveData<String>()
    val authentication: LiveData<String> = _authentication

    private val _loading: MutableLiveData<Boolean> = MutableLiveData()
    val loading: LiveData<Boolean> = _loading

    private val _error: MutableLiveData<String> = MutableLiveData()
    val error: LiveData<String> = _error

    fun login(user: LoginBody) {
        _loading.value = true
        viewModelScope.launch(Dispatchers.IO) {
            if (loginValidateInputUseCase.invoke(user.email, user.password)) {
                runCatching {
                    loginUseCase.invoke(user)
                }.onFailure { exception ->
                    withContext(Dispatchers.Main) {
                        _loading.value = false
                        _error.value = exception.message
                    }
                }.onSuccess { value ->
                    withContext(Dispatchers.Main) {
                        _loading.value = false
                        _login.value = value.message
                        _authentication.value = value.token!!
                    }
                }
            } else {
                withContext(Dispatchers.Main) {
                    _error.value = "Username or password is not valid!"
                    _loading.value = false
                }
            }
        }
    }
}