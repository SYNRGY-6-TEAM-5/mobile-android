package com.synrgy.aeroswift.presentation.viewmodel.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.synrgy.domain.Resource
import com.synrgy.presentation.usecase.auth.ClearTokenUseCase
import com.synrgy.presentation.usecase.auth.GetNameUseCase
import com.synrgy.presentation.usecase.auth.GetPhotoUseCase
import com.synrgy.presentation.usecase.auth.SetNameUseCase
import com.synrgy.presentation.usecase.auth.SetPhotoUseCase
import com.synrgy.presentation.usecase.auth.SetRegTokenUseCase
import com.synrgy.presentation.usecase.login.GetTokenUseCase
import com.synrgy.presentation.usecase.login.SetTokenUseCase
import com.synrgy.presentation.usecase.user.GetUserDetailUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
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
    private val setNameUseCase: SetNameUseCase,
    private val getPhotoUseCase: GetPhotoUseCase,
    private val setPhotoUseCase: SetPhotoUseCase,
    private val setRegTokenUseCase: SetRegTokenUseCase,
    private val getUserDetailUseCase: GetUserDetailUseCase
): ViewModel() {
    private val _logoutLoading: MutableLiveData<Boolean> = MutableLiveData()
    val logoutLoading: LiveData<Boolean> = _logoutLoading

    private val _authentication = MutableLiveData<String>()
    val authentication: LiveData<String> = _authentication

    private val _name = MutableLiveData<String>()
    val name: LiveData<String> = _name

    private val _photo = MutableLiveData<String>()
    val photo: LiveData<String> = _photo

    private val _dateBirth = MutableLiveData<Long>()
    val dateBirth: LiveData<Long> = _dateBirth

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    fun checkAuth() {
        viewModelScope.launch(Dispatchers.Main) {
            _authentication.value = getTokenUseCase.invoke().first() ?: ""
            _name.value = getNameUseCase.invoke().first() ?: "User"
            _photo.value = getPhotoUseCase.invoke().first() ?: ""
        }
    }

    fun setToken(token: String) {
        viewModelScope.launch(Dispatchers.IO) {
            setTokenUseCase.invoke(token)
        }
    }

    fun setRegToken(token: String) {
        viewModelScope.launch(Dispatchers.IO) {
            setRegTokenUseCase.invoke(token)
        }
    }

    fun logout() {
        _logoutLoading.value = true
        viewModelScope.launch(Dispatchers.IO) {
            delay(1000)
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

    fun setPhoto(photoUrl: String) {
        viewModelScope.launch(Dispatchers.IO) {
            setPhotoUseCase.invoke(photoUrl)
        }
    }

    fun getUser() {
        _loading.value = true
        viewModelScope.launch(Dispatchers.IO) {
            when (val response = getUserDetailUseCase.invoke(
                getTokenUseCase.invoke().first() ?: ""
            )) {
                is Resource.Success -> {
                    viewModelScope.launch(Dispatchers.Main) {
                        _loading.value = false
                        _name.value = (response.data?.fullName ?: "User").toString()
                        _photo.value = (response.data?.imageUrl ?: "").toString()
                        _dateBirth.value = response.data?.dob ?: 0L
                    }
                }
                is Resource.ErrorRes -> {
                    clearTokenUseCase.invoke()
                    withContext(Dispatchers.Main) {
                        _loading.value = false
                        if (response.errorRes?.errors == null) {
                            _error.value = response.errorRes?.message ?: ""
                        }
                    }
                }
                is Resource.ExceptionRes -> {
                    clearTokenUseCase.invoke()
                    withContext(Dispatchers.Main) {
                        _loading.value = false
                        _error.value = response.exceptionRes?.message ?: ""
                    }
                }
            }
        }
    }
}