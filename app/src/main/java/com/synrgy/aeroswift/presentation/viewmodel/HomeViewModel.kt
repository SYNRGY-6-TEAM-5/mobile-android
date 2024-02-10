package com.synrgy.aeroswift.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.synrgy.data.local.room.FlightDatabase
import com.synrgy.data.local.room.entity.toEntity
import com.synrgy.data.local.room.entity.toFlightSearch
import com.synrgy.domain.local.FlightSearch
import com.synrgy.presentation.usecase.auth.GetUserIdUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getUserIdUseCase: GetUserIdUseCase,
    private val flightDatabase: FlightDatabase
) : ViewModel() {
    private val _origin = MutableLiveData<String>()
    val origin: LiveData<String> = _origin

    private val _destination = MutableLiveData<String>()
    val destination: LiveData<String> = _destination

    private val _oCity = MutableLiveData<String>()
    val oCity: LiveData<String> = _oCity

    private val _dCity = MutableLiveData<String>()
    val dCity: LiveData<String> = _dCity

    private val _depDate = MutableLiveData<String>()
    val depDate: LiveData<String> = _depDate

    private val _retDate = MutableLiveData<String>()
    val retDate: LiveData<String> = _retDate

    private val _tripType = MutableLiveData<String>()
    val tripType: LiveData<String> = _tripType

    private val _ticketClass = MutableLiveData<String>()
    val ticketClass: LiveData<String> = _ticketClass

    private val _adultSeat = MutableLiveData<Int>()
    val adultSeat: LiveData<Int> = _adultSeat

    private val _childSeat = MutableLiveData<Int>()
    val childSeat: LiveData<Int> = _childSeat

    private val _totalSeat = MutableLiveData<Int>()
    val totalSeat: LiveData<Int> = _totalSeat

    private val _infantSeat = MutableLiveData<Int>()
    val infantSeat: LiveData<Int> = _infantSeat

    private val _flightSearch = MutableLiveData<FlightSearch>()
    val flightSearch: LiveData<FlightSearch> = _flightSearch

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading

    fun setOrigin(data: String) {
        viewModelScope.launch(Dispatchers.Main) {
            _origin.value = data
        }
    }

    fun setDestination(data: String) {
        viewModelScope.launch(Dispatchers.Main) {
            _destination.value = data
        }
    }

    fun setOCity(data: String) {
        viewModelScope.launch(Dispatchers.Main) {
            _oCity.value = data
        }
    }

    fun setDCity(data: String) {
        viewModelScope.launch(Dispatchers.Main) {
            _dCity.value = data
        }
    }

    fun setDepDate(data: String) {
        viewModelScope.launch(Dispatchers.Main) {
            _depDate.value = data
        }
    }

    fun setRetDate(data: String) {
        viewModelScope.launch(Dispatchers.Main) {
            _retDate.value = data
        }
    }

    fun setTripType(data: String) {
        viewModelScope.launch(Dispatchers.Main) {
            _tripType.value = data
        }
    }

    fun setTicketClass(data: String) {
        viewModelScope.launch(Dispatchers.Main) {
            _ticketClass.value = data
        }
    }

    fun setAdultSeat(data: Int) {
        viewModelScope.launch(Dispatchers.Main) {
            _adultSeat.value = data
        }
    }

    fun setChildSeat(data: Int) {
        viewModelScope.launch(Dispatchers.Main) {
            _childSeat.value = data
        }
    }

    fun setTotalSeat(data: Int) {
        viewModelScope.launch(Dispatchers.Main) {
            _totalSeat.value = data
        }
    }

    fun setInfantSeat(data: Int) {
        viewModelScope.launch(Dispatchers.Main) {
            _infantSeat.value = data
        }
    }

    fun setFlightSearch() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val userId = getUserIdUseCase.invoke().first()!!

                withContext(Dispatchers.Main) {
                    val data = FlightSearch(
                        id = userId,
                        origin = _origin.value,
                        destination = _destination.value,
                        oCity = _oCity.value,
                        dCity = _dCity.value,
                        depDate = _depDate.value,
                        retDate = _retDate.value,
                        tripType = _tripType.value,
                        ticketClass = _ticketClass.value,
                        adultSeat = _adultSeat.value,
                        childSeat = _childSeat.value,
                        totalSeat = _totalSeat.value,
                        infantSeat = _infantSeat.value
                    )

                    _flightSearch.value = data

                    withContext(Dispatchers.IO) {
                        flightDatabase.flightSearchDao().insertData(data.toEntity())
                    }
                }
            } catch (_: Exception) {}
        }
    }

    fun getFlightSearch() {
        _loading.value = true

        viewModelScope.launch(Dispatchers.IO) {
            try {
                val userId = getUserIdUseCase.invoke().first()!!
                val data = flightDatabase.flightSearchDao().selectData(userId).toFlightSearch()

                withContext(Dispatchers.Main) {
                    _flightSearch.value = data
                }
            } catch (_: Exception) {}

            withContext(Dispatchers.Main) {
                delay(500)
                _loading.value = false
            }
        }
    }
}