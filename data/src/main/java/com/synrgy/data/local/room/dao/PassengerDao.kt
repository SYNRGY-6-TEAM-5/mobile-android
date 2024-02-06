package com.synrgy.data.local.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.synrgy.data.local.room.entity.PassengerEntity

@Dao
interface PassengerDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertData(entity: PassengerEntity)

    @Query("SELECT * FROM passengers WHERE userId = :userId")
    suspend fun selectData(userId: String): List<PassengerEntity>

    @Query("SELECT * FROM passengers WHERE id = :id")
    suspend fun selectDataById(id: String): PassengerEntity

    @Query("DELETE FROM passengers WHERE id = :id")
    suspend fun deleteData(id: String)
}