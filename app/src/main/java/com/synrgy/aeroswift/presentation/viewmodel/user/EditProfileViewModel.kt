package com.synrgy.aeroswift.presentation.viewmodel.user

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.synrgy.domain.Resource
import com.synrgy.domain.body.user.EditProfileBody
import com.synrgy.domain.response.error.ErrorItem
import com.synrgy.domain.response.user.EditProfileResponse
import com.synrgy.presentation.usecase.auth.GetRegTokenUseCase
import com.synrgy.presentation.usecase.user.EditProfileUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class EditProfileViewModel @Inject constructor(
    private val editProfileUseCase: EditProfileUseCase,
    private val getRegTokenUseCase: GetRegTokenUseCase
): ViewModel() {
    private val _editProfile: MutableLiveData<EditProfileResponse> = MutableLiveData()
    val editProfile: LiveData<EditProfileResponse> = _editProfile

    private val _loading: MutableLiveData<Boolean> = MutableLiveData()
    val loading: LiveData<Boolean> = _loading

    private val _error: MutableLiveData<String> = MutableLiveData()
    val error: LiveData<String> = _error

    private val _errors: MutableLiveData<List<ErrorItem?>?> = MutableLiveData()
    val errors: LiveData<List<ErrorItem?>?> = _errors

    fun editProfile(body: EditProfileBody) {
        _loading.value = true
        viewModelScope.launch(Dispatchers.IO) {
            when (val response = editProfileUseCase.invoke(
                getRegTokenUseCase.invoke().first() ?: "",
                body
            )) {
                is Resource.Success -> {
                    withContext(Dispatchers.Main) {
                        _loading.value = false
                        _editProfile.value = EditProfileResponse(
                            message = response.data?.message ?: "",
                            success = response.data?.success ?: true
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