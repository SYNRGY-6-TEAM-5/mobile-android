package com.synrgy.domain.local

data class AddonData(
    var id: String = "",
    var userId: String = "",
    val userName: String,
    val category: String,
    val weight: Int? = null,
    val price: Long,
    val mealName: String? = null
)