package captiom.core.model.device;

import captiom.core.infrastructure.device.DeviceNotifier;

public class DeviceService {

	private final DeviceNotifier notifier;

	public DeviceService(DeviceNotifier notifier) {
		this.notifier = notifier;
	}

	public DeviceNotifier.DeviceLink using(String deviceId) {
		return notifier.device(deviceId);
	}

	public void register(Device device) {
		throw new UnsupportedOperationException();
	}
}
