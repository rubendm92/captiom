package captiom.server.infrastructure.repositories;

import captiom.core.model.device.DeviceRepository;
import captiom.core.model.device.Device;

import java.util.*;

public class InMemoryDeviceRepository implements DeviceRepository {

	private final Map<String, Device> devices = new HashMap<>();

	@Override
	public void save(Device device) {
		devices.put(device.id(), device);
	}

	@Override
	public List<Device> all() {
		return new ArrayList<>(devices.values());
	}

	@Override
	public Device get(String deviceId) {
		return devices.get(deviceId);
	}
}
