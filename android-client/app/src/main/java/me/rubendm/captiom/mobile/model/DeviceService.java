package me.rubendm.captiom.mobile.model;

import me.rubendm.captiom.mobile.infrastructure.DeviceRepository;

public class DeviceService {

    private final DeviceRepository repository;

    public DeviceService(DeviceRepository repository) {
        this.repository = repository;
    }

    public void save(Device device) {
        repository.save(device);
    }
}
