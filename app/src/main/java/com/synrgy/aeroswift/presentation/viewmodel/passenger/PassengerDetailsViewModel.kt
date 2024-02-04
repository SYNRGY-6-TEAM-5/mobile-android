package com.synrgy.aeroswift.presentation.viewmodel.passenger

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.synrgy.data.local.room.FlightDatabase
import com.synrgy.data.local.room.entity.toEntity
import com.synrgy.data.local.room.entity.toPassenger
import com.synrgy.domain.local.PassengerData
import com.synrgy.presentation.usecase.auth.GetUserIdUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class PassengerDetailsViewModel @Inject constructor(
    private val getUserIdUseCase: GetUserIdUseCase,
    private val flightDatabase: FlightDatabase
): ViewModel() {
    private val _passenger: MutableLiveData<PassengerData> = MutableLiveData()
    val passenger: LiveData<PassengerData> = _passenger

    private val _passengers: MutableLiveData<List<PassengerData>> = MutableLiveData()
    val passengers: LiveData<List<PassengerData>> = _passengers

    private val _loading: MutableLiveData<Boolean> = MutableLiveData()
    val loading: LiveData<Boolean> = _loading

    private val _success: MutableLiveData<Boolean> = MutableLiveData()
    val success: LiveData<Boolean> = _success

    fun addPassenger(item: PassengerData) {
        _loading.value = true
        _success.value = false

        viewModelScope.launch(Dispatchers.IO) {
            try {
                val userId = getUserIdUseCase.invoke().first()!!
                item.userId = userId
                flightDatabase.passengerDao().insertData(item.toEntity())

                withContext(Dispatchers.Main) {
                    _success.value = true
                }
            } catch (e: Exception) {
                Log.d("ERR_MESSAGE", e.message.toString())
            } finally {
                withContext(Dispatchers.Main) {
                    _loading.value = false
                }
            }
        }
    }

    fun getPassenger(id: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val data = flightDatabase.passengerDao().selectDataById(id).toPassenger()

                withContext(Dispatchers.Main) {
                    _passenger.value = data
                }
            } catch (e: Exception) {
                Log.d("ERR_MESSAGE", e.message.toString())
            }
        }
    }

    fun getPassengers() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val userId = getUserIdUseCase.invoke().first()!!
                val data = flightDatabase.passengerDao().selectData(userId).toPassenger()

                withContext(Dispatchers.Main) {
                    _passengers.value = data
                }
            } catch (e: Exception) {
                Log.d("ERR_MESSAGE", e.message.toString())
            }
        }
    }

    fun deletePassenger() {
        _loading.value = true
        viewModelScope.launch(Dispatchers.IO) {
            try {
//                flightDatabase.passengerDao().deleteData(id)
//
//                withContext(Dispatchers.Main) {
//                    _passengers.value = emptyList()
//                }
            } catch (e: Exception) {
                Log.d("ERR_MESSAGE", e.message.toString())
            } finally {
                withContext(Dispatchers.Main) {
                    _loading.value = false
                }
            }
        }
    }
}