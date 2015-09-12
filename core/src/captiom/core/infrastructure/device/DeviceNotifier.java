package captiom.core.infrastructure.device;

import captiom.core.model.device.Eye;
import captiom.core.model.device.OptotypeCharacter;

public interface DeviceNotifier {

	DeviceLink device(String deviceId);

	interface DeviceLink {
		void drawChar(OptotypeCharacter character, double height, Eye eye);
		void clear();
	}
}
