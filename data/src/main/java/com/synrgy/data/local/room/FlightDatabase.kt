package com.synrgy.data.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.synrgy.data.local.room.dao.AddonDao
import com.synrgy.data.local.room.dao.FlightSearchDao
import com.synrgy.data.local.room.dao.RecentAirportDao
import com.synrgy.data.local.room.dao.UserDao
import com.synrgy.data.local.room.entity.AddonEntity
import com.synrgy.data.local.room.entity.FlightSearchEntity
import com.synrgy.data.local.room.entity.RecentAirportEntity
import com.synrgy.data.local.room.entity.UserEntity

@Database(
    entities = [
        UserEntity::class,
        RecentAirportEntity::class,
        FlightSearchEntity::class,
        AddonEntity::class
    ],
    version = 1,
    exportSchema = true
)
abstract class FlightDatabase: RoomDatabase() {
    companion object {
        const val DATABASE_NAME = "flightDB"
    }

    abstract fun userDao(): UserDao
    abstract fun recentAirportDao(): RecentAirportDao
    abstract fun flightSearchDao(): FlightSearchDao
    abstract fun addonDao(): AddonDao
}