package com.ctucl.busstationsettings.data.mapper

import com.ctucl.busstationsettings.data.local.DeviceEntity

fun DeviceEntity.asExternalModel(): DeviceEntity = DeviceEntity(
    id,ip,description
)

fun DeviceEntity.toEntity(): DeviceEntity = DeviceEntity(
    id,ip,description
)