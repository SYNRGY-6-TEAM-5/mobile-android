package com.synrgy.aeroswift.presentation.viewmodel.airport

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.synrgy.domain.Resource
import com.synrgy.domain.response.airport.AirportData
import com.synrgy.domain.response.airport.AirportResponse
import com.synrgy.presentation.usecase.airport.AirportListUseCase
import com.synrgy.presentation.usecase.airport.GetRecentAirportUseCase
import com.synrgy.presentation.usecase.airport.SetRecentAirportUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class AirportListViewModel @Inject constructor(
    private val airportListUseCase: AirportListUseCase,
    private val setRecentAirportUseCase: SetRecentAirportUseCase,
    private val getRecentAirportUseCase: GetRecentAirportUseCase
): ViewModel() {
    private val _airport: MutableLiveData<AirportResponse> = MutableLiveData()
    val airport: LiveData<AirportResponse> = _airport

    private val _recentAirport: MutableLiveData<MutableSet<String>?> = MutableLiveData()
    val recentAirport: LiveData<MutableSet<String>?> = _recentAirport

    private val _origin: MutableLiveData<AirportData> = MutableLiveData()
    val origin: LiveData<AirportData> = _origin

    private val _destination: MutableLiveData<AirportData> = MutableLiveData()
    val destination: LiveData<AirportData> = _destination

    private val _isDest: MutableLiveData<Boolean> = MutableLiveData()
    val isDest: LiveData<Boolean> = _isDest

    private val _loading: MutableLiveData<Boolean> = MutableLiveData()
    val loading: LiveData<Boolean> = _loading

    fun getAirport() {
        _loading.value = true
        viewModelScope.launch(Dispatchers.IO) {
            when (val response = airportListUseCase.invoke()) {
                is Resource.Success -> {
                    withContext(Dispatchers.Main) {
                        _loading.value = false
                        _airport.value = AirportResponse(
                            data = response.data?.data ?: emptyList(),
                            status = response.data?.status ?: true
                        )
                    }
                }
                else -> {}
            }
        }
    }

    fun addRecentAirport(item: AirportData) {
        viewModelScope.launch(Dispatchers.IO) {
            setRecentAirportUseCase.invoke(item)
        }
    }

    fun getRecentAirport() {
        viewModelScope.launch(Dispatchers.IO) {
            val response = getRecentAirportUseCase.invoke()
            withContext(Dispatchers.Main) {
                _recentAirport.value = response
            }
        }
    }

    fun setIsDest(value: Boolean) {
        viewModelScope.launch(Dispatchers.Main) {
            _isDest.value = value
        }
    }

    fun setOrigin(item: AirportData) {
        viewModelScope.launch(Dispatchers.Main) {
            _origin.value = item
        }
    }

    fun setDestination(item: AirportData) {
        viewModelScope.launch(Dispatchers.Main) {
            _destination.value = item
        }
    }
}