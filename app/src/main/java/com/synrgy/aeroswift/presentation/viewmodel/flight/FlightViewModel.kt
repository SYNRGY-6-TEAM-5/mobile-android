package com.synrgy.aeroswift.presentation.viewmodel.flight

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.synrgy.data.remote.NodeRepository
import com.synrgy.domain.Resource
import com.synrgy.domain.response.flight.FlightData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class FlightViewModel @Inject constructor(
    private val nodeRepository: NodeRepository
): ViewModel() {
    private val _flights = MutableLiveData<ArrayList<FlightData>>()
    val flights: LiveData<ArrayList<FlightData>> = _flights

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    fun getFlights(
        departureAirport: String = "",
        arrivalAirport: String = "",
        departureDate: String = ""
    ) {
        _loading.value = true

        viewModelScope.launch(Dispatchers.IO) {
            when (val response = nodeRepository.getFlights(departureAirport, arrivalAirport, departureDate)) {
                is Resource.Success -> {
                    withContext(Dispatchers.Main) {
                        _flights.value = ArrayList(response.data?.data ?: emptyList())
                    }
                }
                is Resource.ErrorRes -> {
                    withContext(Dispatchers.Main) {
                        if (response.errorRes?.errors == null) {
                            _error.value = response.errorRes?.message ?: ""
                        }
                    }
                }
                is Resource.ExceptionRes -> {
                    withContext(Dispatchers.Main) {
                        _error.value = response.exceptionRes?.message ?: "Server error"
                    }
                }
                else -> {}
            }

            withContext(Dispatchers.Main) {
                _loading.value = false
            }
        }
    }
}