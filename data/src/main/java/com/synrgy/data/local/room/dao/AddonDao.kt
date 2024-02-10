package com.synrgy.data.local.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.synrgy.data.local.room.entity.AddonEntity

@Dao
interface AddonDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertData(entity: AddonEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(entities: List<AddonEntity>)

    @Query("SELECT * FROM addons WHERE userId = :userId")
    suspend fun selectData(userId: String): List<AddonEntity>

    @Query("SELECT * FROM addons WHERE passengerId = :passengerId")
    suspend fun selectDataByPassengerId(passengerId: String): List<AddonEntity>

    @Query("DELETE FROM addons WHERE userId = :userId")
    suspend fun deleteData(userId: String)

    @Query("DELETE FROM addons WHERE userId = :userId AND category = :category")
    suspend fun deleteData(userId: String, category: String)

    suspend fun deleteAndInsertAll(entities: List<AddonEntity>, userId: String, category: String) {
        deleteData(userId, category)
        insertAll(entities)
    }
}