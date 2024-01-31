package com.synrgy.aeroswift.presentation.viewmodel.passenger

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PassengerDetailsViewModel @Inject constructor(): ViewModel() {
    private val _loading: MutableLiveData<Boolean> = MutableLiveData()
    val loading: LiveData<Boolean> = _loading

    fun deletePassenger() {
        _loading.value = true
        viewModelScope.launch(Dispatchers.Main) {
            delay(1000)
            _loading.value = false
        }
    }
}