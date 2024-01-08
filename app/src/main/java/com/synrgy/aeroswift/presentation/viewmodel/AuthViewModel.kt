package com.synrgy.aeroswift.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.synrgy.presentation.usecase.auth.ClearTokenUseCase
import com.synrgy.presentation.usecase.auth.GetNameUseCase
import com.synrgy.presentation.usecase.auth.SetNameUseCase
import com.synrgy.presentation.usecase.login.GetTokenUseCase
import com.synrgy.presentation.usecase.login.SetTokenUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val getTokenUseCase: GetTokenUseCase,
    private val clearTokenUseCase: ClearTokenUseCase,
    private val setTokenUseCase: SetTokenUseCase,
    private val getNameUseCase: GetNameUseCase,
    private val setNameUseCase: SetNameUseCase
): ViewModel() {
    private val _logoutLoading: MutableLiveData<Boolean> = MutableLiveData()
    val logoutLoading: LiveData<Boolean> = _logoutLoading

    private val _error: MutableLiveData<String> = MutableLiveData()
    val error: LiveData<String> = _error

    private val _authentication = MutableLiveData<String>()
    val authentication: LiveData<String> = _authentication

    private val _name = MutableLiveData<String>()
    val name: LiveData<String> = _name

    fun checkAuth() {
        viewModelScope.launch(Dispatchers.Main) {
            _authentication.value = getTokenUseCase.invoke().first() ?: ""
            _name.value = getNameUseCase.invoke().first() ?: ""
        }
    }

    fun setToken(token: String) {
        viewModelScope.launch(Dispatchers.IO) {
            setTokenUseCase.invoke(token)
        }
    }

    fun logout() {
        _logoutLoading.value = true
        viewModelScope.launch(Dispatchers.IO) {
            clearTokenUseCase.invoke()
            withContext(Dispatchers.Main) {
                _logoutLoading.value = false
            }
        }
    }

    fun setName(name: String) {
        viewModelScope.launch(Dispatchers.IO) {
            setNameUseCase.invoke(name)
        }
    }
}