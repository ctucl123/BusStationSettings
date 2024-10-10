package com.ctucl.busstationsettings.data.local

import com.ctucl.busstationsettings.data.mapper.asExternalModel
import com.ctucl.busstationsettings.data.mapper.toEntity
import com.ctucl.busstationsettings.domain.repository.DeviceRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class DeviceRepositoryImpl(
    private val dao:DeviceDao
):DeviceRepository{

    override fun getAllDevices(): Flow<List<DeviceEntity>> {
        return dao.getDevices()
            .map {
                devices -> devices.map {
                    it.asExternalModel()
                }
            }
    }

    override suspend fun insertDevice(device: DeviceEntity) {
        dao.insertDevice(device.toEntity())
    }

    override suspend fun deleteDevice(device: DeviceEntity) {
        dao.deleteDevice(device.toEntity())
    }

    override suspend fun updateDevice(device: DeviceEntity) {
        dao.updateDevice(device.toEntity())
    }

}