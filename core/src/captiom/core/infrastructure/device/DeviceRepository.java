package captiom.core.infrastructure.device;

import captiom.core.model.device.Device;

public interface DeviceRepository {

	Device save(Device device);
}
