package captiom.core.model.device;

public class DeviceService {

	public DeviceLink using(String deviceId) {
		return null;
	}

	@FunctionalInterface
	public interface DeviceLink {
		void drawChar(OptotypeCharacter character, double height, Eye eye);
	}
}
