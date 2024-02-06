package com.synrgy.data.local.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.synrgy.data.local.room.entity.AddonEntity
import com.synrgy.data.local.room.entity.DocumentEntity

@Dao
interface DocumentDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertData(entity: DocumentEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(entities: List<DocumentEntity>)

    @Query("SELECT * FROM documents WHERE passengerId = :passengerId")
    suspend fun selectData(passengerId: String): List<DocumentEntity>

    @Query("DELETE FROM documents WHERE passengerId = :passengerId")
    suspend fun deleteData(passengerId: String)
}