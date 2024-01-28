package com.synrgy.aeroswift.presentation.viewmodel.forgotpassword

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.synrgy.domain.Resource
import com.synrgy.domain.body.forgotpassword.EditPasswordFpBody
import com.synrgy.domain.response.error.ErrorItem
import com.synrgy.domain.response.forgotpassword.EditPasswordFpResponse
import com.synrgy.presentation.usecase.auth.GetRegTokenUseCase
import com.synrgy.presentation.usecase.forgotpassword.EditPasswordFpUseCase
import com.synrgy.presentation.usecase.login.ValidateChangePassUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class EditPasswordFpViewModel @Inject constructor(
    private val editPasswordFpUseCase: EditPasswordFpUseCase,
    private val getRegTokenUseCase: GetRegTokenUseCase,
    private val validateChangePassUseCase: ValidateChangePassUseCase
): ViewModel() {
    private val _editPassword: MutableLiveData<EditPasswordFpResponse> = MutableLiveData()
    val editPassword: LiveData<EditPasswordFpResponse> = _editPassword

    private val _loading: MutableLiveData<Boolean> = MutableLiveData()
    val loading: LiveData<Boolean> = _loading

    private val _error: MutableLiveData<String> = MutableLiveData()
    val error: LiveData<String> = _error

    private val _errors: MutableLiveData<List<ErrorItem?>?> = MutableLiveData()
    val errors: LiveData<List<ErrorItem?>?> = _errors

    private val _localError: MutableLiveData<Boolean> = MutableLiveData()
    val localError: LiveData<Boolean> = _localError

    fun editPassword(body: EditPasswordFpBody) {
        _loading.value = true
        _localError.value = false

        viewModelScope.launch(Dispatchers.IO) {
            if (!validateChangePassUseCase.invoke(
                body.newPassword,
                body.retypePassword
            )) {
                withContext(Dispatchers.Main) {
                    _loading.value = false
                    _localError.value = true
                }
            } else {
                when (val response = editPasswordFpUseCase.invoke(
                    getRegTokenUseCase.invoke().first() ?: "",
                    body
                )) {
                    is Resource.Success -> {
                        withContext(Dispatchers.Main) {
                            _loading.value = false
                            _editPassword.value = EditPasswordFpResponse(
                                message = response.data?.message ?: "",
                                status = response.data?.status ?: true
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
}