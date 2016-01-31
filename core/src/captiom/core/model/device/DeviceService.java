package captiom.core.model.device;

import java.util.List;

public class DeviceService {

	private final DeviceRepository repository;
	private final DeviceNotifier notifier;

	public DeviceService(DeviceRepository repository, DeviceNotifier notifier) {
		this.repository = repository;
		this.notifier = notifier;
	}

	public DeviceNotifier.DeviceLink using(String deviceId) {
		return notifier.device(get(deviceId));
	}

	public void register(Device device) {
		repository.save(device);
	}

	public List<Device> all() {
		return repository.all();
	}

	public Device get(String deviceId) {
		return repository.get(deviceId);
	}
}
