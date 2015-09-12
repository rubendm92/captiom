package captiom.core.infrastructure.device;

import captiom.core.model.device.Device;

import java.util.List;

public interface DeviceRepository {

	Device save(Device device);
	List<Device> all();
}
