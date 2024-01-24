package com.synrgy.data.local

import android.content.Context
import android.util.Log
import com.synrgy.domain.response.airport.AirportData
import javax.inject.Inject

class SharedPreferences @Inject constructor (
    private val context: Context
) {
    companion object {
        private const val KEY_SHARED_PREFERENCES = "SHARED_PREFERENCES"
        private const val KEY_RECENT_AIRPORT = "KEY_RECENT_AIRPORT"
    }

    private val sharedPreference = context.getSharedPreferences(KEY_SHARED_PREFERENCES, Context.MODE_PRIVATE)

    fun setRecentAirport(data: AirportData) {
        val editor = sharedPreference.edit()
        val set = HashSet<String>()

        set.add(data.id.toString())
        editor.putStringSet(KEY_RECENT_AIRPORT, set)
        editor.apply()
    }

    fun getRecentAirport(): MutableSet<String>? {
        val set = HashSet<String>()
        val airport = sharedPreference.getStringSet(KEY_SHARED_PREFERENCES, set)

        if (airport != null) {
            for (str in airport) {
                Log.d("RECENT_ID", str)
            }
        }

//        if (airport != null) {
//            if (airport.size > 3) airport.remove(airport.first())
//        }

        return airport
    }
}