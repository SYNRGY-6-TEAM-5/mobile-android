package com.synrgy.data.local.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.synrgy.domain.local.AddonData

@Entity(tableName = "addons")
data class AddonEntity(
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: String,

    @ColumnInfo(name = "userId")
    val userId: String,

    @ColumnInfo(name = "passengerId")
    val passengerId: String,

    @ColumnInfo(name = "userName")
    val userName: String,

    @ColumnInfo(name = "category")
    val category: String,

    @ColumnInfo(name = "weight")
    val weight: Int?,

    @ColumnInfo(name = "price")
    val price: Long,

    @ColumnInfo(name = "mealName")
    val mealName: String?
)

fun List<AddonEntity>.toAddon(): List<AddonData> {
    return this.map {
        AddonData(it.id, it.userId, it.passengerId, it.userName, it.category, it.weight, it.price, it.mealName)
    }
}

fun AddonData.toEntity(): AddonEntity {
    return AddonEntity(id, userId, passengerId, userName, category, weight, price, mealName)
}

fun List<AddonData>.toEntity(): List<AddonEntity> {
    return this.map {
        AddonEntity(it.id, it.userId, it.passengerId, it.userName, it.category, it.weight, it.price, it.mealName)
    }
}