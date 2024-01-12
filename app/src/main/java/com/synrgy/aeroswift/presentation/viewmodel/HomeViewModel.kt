package com.synrgy.aeroswift.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor() : ViewModel() {

    private val _selectedClass = MutableLiveData<String>()

    private val _totalPassengers = MutableLiveData<Int>()

    private val _tripType = MutableLiveData<String>()
    val selectedClass: LiveData<String> get() = _selectedClass
    val totalPassengers: LiveData<Int> get() = _totalPassengers
    val tripType: LiveData<String> get() = _tripType

    fun setSelectedClass(selectedClass: String) {
        _selectedClass.value = selectedClass
    }

    fun setTotalPassengers(totalPassengers: Int) {
        _totalPassengers.value = totalPassengers
    }

    fun setTripType(tripType: String) {
        _tripType.value = tripType
    }
}