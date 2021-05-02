package com.yp.www.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class DeviceEntity(
    @PrimaryKey val id: Int,
    var name: String,
    var age: Int,
    var sex: Boolean = true
)