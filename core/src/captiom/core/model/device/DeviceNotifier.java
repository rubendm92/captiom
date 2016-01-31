package captiom.core.model.device;

public interface DeviceNotifier {

	DeviceLink device(Device device);

	interface DeviceLink {
		void drawChar(OptotypeCharacter character, double charHeight, Eye eye);

		void clear();
	}
}
