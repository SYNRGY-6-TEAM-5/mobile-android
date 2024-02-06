package com.synrgy.data.local.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.synrgy.data.local.room.entity.FlightSearchEntity

@Dao
interface FlightSearchDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertData(entity: FlightSearchEntity)

    @Query("SELECT * FROM flight_searches WHERE id = :userId")
    suspend fun selectData(userId: String): FlightSearchEntity

    @Query("DELETE FROM flight_searches WHERE id = :userId")
    suspend fun deleteData(userId: String)
}