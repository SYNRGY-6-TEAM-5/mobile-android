package com.synrgy.aeroswift.presentation.viewmodel.ticket

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.synrgy.domain.Resource
import com.synrgy.domain.response.ticket.TicketData
import com.synrgy.presentation.usecase.ticket.GetTicketsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class TicketViewModel @Inject constructor(
    private val getTicketsUseCase: GetTicketsUseCase
): ViewModel() {
    private val _tickets = MutableLiveData<ArrayList<TicketData>>()
    val tickets: LiveData<ArrayList<TicketData>> = _tickets

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    fun getTickets(
        departureAirport: String,
        arrivalAirport: String,
        departureDate: String
    ) {
        _loading.value = true

        viewModelScope.launch(Dispatchers.IO) {
            when (val response = getTicketsUseCase.invoke(departureAirport, arrivalAirport, departureDate)) {
                is Resource.Success -> {
                    withContext(Dispatchers.Main) {
                        _tickets.value = ArrayList(response.data?.data ?: emptyList())
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