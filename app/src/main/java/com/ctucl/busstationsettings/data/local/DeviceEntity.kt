package com.ctucl.busstationsettings.data.local
import androidx.room.Entity
import androidx.room.PrimaryKey
@Entity(tableName = "devices_table")
data class DeviceEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val ip: String,
    val description: String
)

