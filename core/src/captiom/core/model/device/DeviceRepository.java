package captiom.core.model.device;

import java.util.List;

public interface DeviceRepository {

	void save(Device device);
	List<Device> all();
	Device get(String deviceId);
}
