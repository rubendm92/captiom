package captiom.core.actions.device;

import captiom.core.model.device.Device;
import captiom.core.model.device.DeviceService;

public class RegisterDeviceAction {

	private final DeviceService service;

	public RegisterDeviceAction(DeviceService service) {
		this.service = service;
	}

	public void register(Device device) {
		service.register(device);
	}
}
