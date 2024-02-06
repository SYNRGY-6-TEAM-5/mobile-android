package com.synrgy.aeroswift.presentation.viewmodel.airport

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.synrgy.data.local.room.FlightDatabase
import com.synrgy.data.local.room.entity.toAirportData
import com.synrgy.data.local.room.entity.toRecentAirportEntity
import com.synrgy.domain.Resource
import com.synrgy.domain.response.airport.AirportData
import com.synrgy.domain.response.airport.AirportResponse
import com.synrgy.presentation.usecase.airport.AirportListUseCase
import com.synrgy.presentation.usecase.auth.GetUserIdUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class AirportListViewModel @Inject constructor(
    private val airportListUseCase: AirportListUseCase,
    private val getUserIdUseCase: GetUserIdUseCase,
    private val flightDatabase: FlightDatabase
): ViewModel() {
    private val _airport: MutableLiveData<AirportResponse> = MutableLiveData()
    val airport: LiveData<AirportResponse> = _airport

    private val _recentAirport: MutableLiveData<List<AirportData>> = MutableLiveData()
    val recentAirport: LiveData<List<AirportData>> = _recentAirport

    private val _isDest: MutableLiveData<Boolean> = MutableLiveData()
    val isDest: LiveData<Boolean> = _isDest

    private val _loading: MutableLiveData<Boolean> = MutableLiveData()
    val loading: LiveData<Boolean> = _loading

    private val _clearLoading: MutableLiveData<Boolean> = MutableLiveData()
    val clearLoading: LiveData<Boolean> = _clearLoading

    private val _error: MutableLiveData<Boolean> = MutableLiveData()
    val error: LiveData<Boolean> = _error

    fun getAirport() {
        _loading.value = true
        _error.value = false

        viewModelScope.launch(Dispatchers.IO) {
            when (val response = airportListUseCase.invoke()) {
                is Resource.Success -> {
                    withContext(Dispatchers.Main) {
                        _airport.value = AirportResponse(
                            data = response.data?.data ?: emptyList(),
                            status = response.data?.status ?: true
                        )
                    }
                }
                else -> {
                    withContext(Dispatchers.Main) {
                        _error.value = true
                    }
                }
            }
            withContext(Dispatchers.Main) {
                _loading.value = false
            }
        }
    }

    fun addRecentAirport(item: AirportData) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val userId = getUserIdUseCase.invoke().first()!!

                flightDatabase.recentAirportDao().insertData(
                    item.toRecentAirportEntity(userId)
                )
            } catch (e: Exception) {
                Log.d("ERR_MESSAGE", e.message.toString())
            }
        }
    }

    fun getRecentAirport() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val userId = getUserIdUseCase.invoke().first()!!

                val response = flightDatabase
                    .recentAirportDao()
                    .selectData(userId)
                    .toAirportData()

                if (response.size > 3) {
                    flightDatabase.recentAirportDao().deleteFirstData(userId)
                }

                withContext(Dispatchers.Main) {
                    _recentAirport.value = response
                }
            } catch (e: Exception) {
                Log.d("ERR_MESSAGE", e.message.toString())
            }
        }
    }

    fun clearRecentAirport() {
        _clearLoading.value = true
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val userId = getUserIdUseCase.invoke().first()!!

                flightDatabase.recentAirportDao().deleteAllData(userId)

                withContext(Dispatchers.Main) {
                    _recentAirport.value = emptyList()
                }
            } catch (e: Exception) {
                Log.d("ERR_MESSAGE", e.message.toString())
            } finally {
                withContext(Dispatchers.Main) {
                    _clearLoading.value = false
                }
            }
        }
    }

    fun setIsDest(value: Boolean) {
        viewModelScope.launch(Dispatchers.Main) {
            _isDest.value = value
        }
    }
}