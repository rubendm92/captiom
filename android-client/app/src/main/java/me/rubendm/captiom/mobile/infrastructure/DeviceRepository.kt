package me.rubendm.captiom.mobile.infrastructure

import me.rubendm.captiom.mobile.model.Device

interface DeviceRepository {
    fun save(device: Device)
}
