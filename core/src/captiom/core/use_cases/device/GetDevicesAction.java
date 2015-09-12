package captiom.core.use_cases.device;

import captiom.core.model.device.Device;
import captiom.core.model.device.DeviceService;

import java.util.List;

public class GetDevicesAction {

	private final DeviceService service;

	public GetDevicesAction(DeviceService service) {
		this.service = service;
	}

	public List<Device> allDevices() {
		return service.all();
	}
}
