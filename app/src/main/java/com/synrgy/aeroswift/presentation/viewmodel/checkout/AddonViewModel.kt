package com.synrgy.aeroswift.presentation.viewmodel.checkout

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.synrgy.data.local.room.FlightDatabase
import com.synrgy.data.local.room.entity.toAddon
import com.synrgy.data.local.room.entity.toEntity
import com.synrgy.domain.local.AddonData
import com.synrgy.presentation.constant.Constant
import com.synrgy.presentation.usecase.auth.GetUserIdUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class AddonViewModel @Inject constructor(
    private val getUserIdUseCase: GetUserIdUseCase,
    private val flightDatabase: FlightDatabase
): ViewModel() {
    private val _addons: MutableLiveData<List<AddonData>> = MutableLiveData()
    val addons: LiveData<List<AddonData>> = _addons

    private val _loading: MutableLiveData<Boolean> = MutableLiveData()
    val loading: LiveData<Boolean> = _loading

    fun saveAddons(item: AddonData) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val userId = getUserIdUseCase.invoke().first()!!
                item.id = "${item.mealName ?: "baggage"}-${userId}"
                item.userId = userId

                flightDatabase.addonDao().insertData(item.toEntity())
            } catch (e: Exception) {
                Log.d("ERR_MESSAGE", e.message.toString())
            }
        }
    }

    fun deleteAndSaveAllMeals(items: List<AddonData>) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val userId = getUserIdUseCase.invoke().first()!!

                items.forEach {
                    it.userId = userId
                    it.id = "${it.mealName}-${it.passengerId}"
                }

                flightDatabase.addonDao().deleteAndInsertAll(
                    items.toEntity(),
                    userId,
                    Constant.AddonType.MEALS.value
                )
            } catch (e: Exception) {
                Log.d("ERR_MESSAGE", e.message.toString())
            }
        }
    }

    fun getAddons() {
        _loading.value = true
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val userId = getUserIdUseCase.invoke().first()!!

                val response = flightDatabase
                    .addonDao()
                    .selectData(userId)
                    .toAddon()

                withContext(Dispatchers.Main) {
                    _addons.value = response
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

    fun getAddonsByPassengerId(passengerId: String) {
        _loading.value = true
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = flightDatabase
                    .addonDao()
                    .selectDataByPassengerId(passengerId)
                    .toAddon()

                withContext(Dispatchers.Main) {
                    _addons.value = response
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

    fun deleteAddons() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val userId = getUserIdUseCase.invoke().first()!!

                flightDatabase.addonDao().deleteData(userId)

                withContext(Dispatchers.Main) {
                    _addons.value = emptyList()
                }
            } catch (e: Exception) {
                Log.d("ERR_MESSAGE", e.message.toString())
            }
        }
    }

    fun deleteAddons(category: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val userId = getUserIdUseCase.invoke().first()!!

                flightDatabase.addonDao().deleteData(userId, category)

                withContext(Dispatchers.Main) {
                    _addons.value = emptyList()
                }
            } catch (e: Exception) {
                Log.d("ERR_MESSAGE", e.message.toString())
            }
        }
    }
}