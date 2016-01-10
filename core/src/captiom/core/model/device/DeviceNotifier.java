package captiom.core.model.device;

import captiom.core.model.device.Device;
import captiom.core.model.device.Eye;
import captiom.core.model.device.OptotypeCharacter;

public interface DeviceNotifier {

	DeviceLink device(Device device);

	interface DeviceLink {
		void drawChar(OptotypeCharacter character, double charHeight, Eye eye);
		void clear();
	}
}
