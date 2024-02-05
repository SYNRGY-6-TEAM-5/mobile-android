package com.synrgy.domain.local

data class PassengerData(
    val id: String,
    var userId: String = "",
    var nik: String = "",
    var name: String,
    var dob: String = "",
    val category: String,
    val surname: String = ""
)
