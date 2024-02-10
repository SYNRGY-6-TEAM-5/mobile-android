package com.synrgy.data.local.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.synrgy.data.local.room.entity.RecentAirportEntity

@Dao
interface RecentAirportDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertData(entity: RecentAirportEntity)

    @Query("SELECT * FROM recent_airports WHERE userId = :userId")
    suspend fun selectData(userId: String): List<RecentAirportEntity>

    @Query("DELETE FROM recent_airports WHERE userId = :userId")
    suspend fun deleteAllData(userId: String)

    @Query("DELETE FROM recent_airports WHERE id = (SELECT id FROM recent_airports WHERE userId = :userId ORDER BY id ASC LIMIT 1)")
    suspend fun deleteFirstData(userId: String)
}