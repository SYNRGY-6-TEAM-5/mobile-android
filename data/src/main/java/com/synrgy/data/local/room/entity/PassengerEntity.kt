package com.synrgy.data.local.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.synrgy.domain.local.PassengerData

@Entity(tableName = "passengers")
data class PassengerEntity(
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: String,

    @ColumnInfo(name = "userId")
    val userId: String,

    @ColumnInfo(name = "nik")
    val nik: String,

    @ColumnInfo(name = "name")
    val name: String,

    @ColumnInfo(name = "dob")
    val dob: String,

    @ColumnInfo(name = "category")
    val category: String
)

fun PassengerEntity.toPassenger(): PassengerData {
    return PassengerData(id, userId, nik, name, dob, category)
}

fun List<PassengerEntity>.toPassenger(): List<PassengerData> {
    return this.map {
        PassengerData(it.id, it.userId, it.nik, it.name, it.dob, it.category)
    }
}

fun PassengerData.toEntity(): PassengerEntity {
    return PassengerEntity(id, userId, nik, name, dob, category)
}

fun List<PassengerData>.toEntity(): List<PassengerEntity> {
    return this.map {
        PassengerEntity(it.id, it.userId, it.nik, it.name, it.dob, it.category)
    }
}