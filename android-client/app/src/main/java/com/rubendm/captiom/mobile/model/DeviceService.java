package com.rubendm.captiom.mobile.model;

import com.rubendm.captiom.mobile.infrastructure.DeviceRepository;

public class DeviceService {

    private final DeviceRepository repository;

    public DeviceService(DeviceRepository repository) {
        this.repository = repository;
    }

    public void save(Device device) {
        repository.save(device);
    }
}
