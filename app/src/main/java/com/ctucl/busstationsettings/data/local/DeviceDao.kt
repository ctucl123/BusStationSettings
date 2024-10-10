package com.ctucl.busstationsettings.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
@Dao
interface DeviceDao {
    @Query("SELECT * FROM devices_table")
    fun getDevices(): Flow<List<DeviceEntity>>
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDevice(device: DeviceEntity)
    @Delete
    suspend fun deleteDevice(toEntity: DeviceEntity)
    @Update
    suspend fun updateDevice(toEntity: DeviceEntity)
}