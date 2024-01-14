package com.synrgy.aeroswift.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.synrgy.domain.RegisterBody
import com.synrgy.presentation.usecase.register.RegisterUseCase
import com.synrgy.presentation.usecase.register.RegisterValidateInputUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val registerUseCase: RegisterUseCase,
    private val registerValidateInputUseCase: RegisterValidateInputUseCase
): ViewModel() {
    private val _register: MutableLiveData<String> = MutableLiveData()
    val register: LiveData<String> = _register

    private val _loading: MutableLiveData<Boolean> = MutableLiveData()
    val loading: LiveData<Boolean> = _loading

    private val _error: MutableLiveData<String> = MutableLiveData()
    val error: LiveData<String> = _error

    fun register(user: RegisterBody) {
        _loading.value = true
        viewModelScope.launch(Dispatchers.IO) {
            if (registerValidateInputUseCase.invoke(user.email, user.password)) {
                runCatching {
                    registerUseCase.invoke(user)
                }.onFailure { exception ->
                    withContext(Dispatchers.Main) {
                        _loading.value = false
                        _error.value = exception.message
                    }
                }.onSuccess { value ->
                    withContext(Dispatchers.Main) {
                        _loading.value = false
                        _register.value = value.message
                    }
                }
            } else {
                withContext(Dispatchers.Main) {
                    _error.value = "Username atau password tidak valid!"
                    _loading.value = false
                }
            }
        }
    }
}