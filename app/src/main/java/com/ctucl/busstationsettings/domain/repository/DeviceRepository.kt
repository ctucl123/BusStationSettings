package com.ctucl.busstationsettings.domain.repository

import com.ctucl.busstationsettings.data.local.DeviceEntity
import kotlinx.coroutines.flow.Flow

interface DeviceRepository {
    fun getAllDevices(): Flow<List<DeviceEntity>>
    suspend fun insertDevice(device: DeviceEntity)
    suspend fun deleteDevice(device: DeviceEntity)
    suspend fun updateDevice(device: DeviceEntity)
}