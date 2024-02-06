package com.synrgy.data.local.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.synrgy.domain.local.DocumentData

@Entity(tableName = "documents")
data class DocumentEntity(
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: String,

    @ColumnInfo(name = "passengerId")
    val passengerId: String,

    @ColumnInfo(name = "type")
    val type: String,

    @ColumnInfo(name = "nationality")
    val nationality: String,

    @ColumnInfo(name = "docNum")
    val docNum: String,

    @ColumnInfo(name = "expiry")
    val expiry: String,

    @ColumnInfo(name = "file")
    val file: String
)

fun DocumentEntity.toDocument(): DocumentData {
    return DocumentData(id, passengerId, type, nationality, docNum, expiry, file)
}

fun List<DocumentEntity>.toDocument(): List<DocumentData> {
    return this.map {
        DocumentData(it.id, it.passengerId, it.type, it.nationality, it.docNum, it.expiry, it.file)
    }
}

fun DocumentData.toEntity(): DocumentEntity {
    return DocumentEntity(id, passengerId, type, nationality, docNum, expiry, file)
}

fun List<DocumentData>.toEntity(): List<DocumentEntity> {
    return this.map {
        DocumentEntity(it.id, it.passengerId, it.type, it.nationality, it.docNum, it.expiry, it.file)
    }
}