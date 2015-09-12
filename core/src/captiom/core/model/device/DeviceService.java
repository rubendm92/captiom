package captiom.core.model.device;

import captiom.core.infrastructure.device.DeviceNotifier;
import captiom.core.infrastructure.device.DeviceRepository;

import java.util.List;

public class DeviceService {

	private final DeviceRepository repository;
	private final DeviceNotifier notifier;

	public DeviceService(DeviceRepository repository, DeviceNotifier notifier) {
		this.repository = repository;
		this.notifier = notifier;
	}

	public DeviceNotifier.DeviceLink using(String deviceId) {
		return notifier.device(deviceId);
	}

	public Device register(Device device) {
		return repository.save(device);
	}

	public List<Device> all() {
		return repository.all();
	}
}