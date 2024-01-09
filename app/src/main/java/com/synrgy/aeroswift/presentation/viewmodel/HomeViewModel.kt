package com.synrgy.aeroswift.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class HomeViewModel : ViewModel() {

    private val _selectedClass = MutableLiveData<String>()
    val selectedClass: LiveData<String> get() = _selectedClass

    fun setSelectedClass(selectedClass: String) {
        _selectedClass.value = selectedClass
    }
}