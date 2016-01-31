package me.rubendm.captiom.mobile.actions

import me.rubendm.captiom.mobile.infrastructure.DeviceRepository
import me.rubendm.captiom.mobile.model.Device

class RegisterDevice {

    private val repository: DeviceRepository;

    constructor(repository: DeviceRepository) {
        this.repository = repository
    }

    fun execute(device: Device) {
        repository.save(device)
    }
}