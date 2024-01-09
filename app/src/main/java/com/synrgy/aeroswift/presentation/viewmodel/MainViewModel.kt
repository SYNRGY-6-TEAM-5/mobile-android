package com.synrgy.aeroswift.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.synrgy.presentation.usecase.onboarding.GetNewUserUseCase
import com.synrgy.presentation.usecase.onboarding.SetNewUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getNewUserUseCase: GetNewUserUseCase,
    private val setNewUserUseCase: SetNewUserUseCase
): ViewModel() {
    private val _newUser: MutableLiveData<Boolean> = MutableLiveData()
    val newUser: LiveData<Boolean> = _newUser

    fun checkNewUser() {
        viewModelScope.launch(Dispatchers.Main) {
            _newUser.value = getNewUserUseCase.invoke().first() ?: true
        }
    }

    fun setNewUser(newUser: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            setNewUserUseCase.invoke(newUser)
        }
    }
}