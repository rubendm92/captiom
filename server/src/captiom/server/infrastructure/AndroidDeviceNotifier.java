package captiom.server.infrastructure;

import captiom.core.infrastructure.device.DeviceNotifier;
import captiom.core.model.device.Eye;
import captiom.core.model.device.OptotypeCharacter;

public class AndroidDeviceNotifier implements DeviceNotifier {

	@Override
	public DeviceLink device(String deviceId) {
		return new AndroidDeviceLink(deviceId);
	}

	private class AndroidDeviceLink implements DeviceLink {

		private final String deviceId;

		public AndroidDeviceLink(String deviceId) {
			this.deviceId = deviceId;
		}

		@Override
		public void drawChar(OptotypeCharacter character, long detailDegrees, Eye eye) {
			GcmNotificationMessage
					.send(message -> parametersToDrawChar(message, character, detailDegrees, eye))
					.to(deviceId);
		}

		@Override
		public void clear() {
			GcmNotificationMessage
					.send(sender -> sender.addData("action", "clear"))
					.to(deviceId);
		}

		private GcmNotificationMessage parametersToDrawChar(GcmNotificationMessage message, OptotypeCharacter character, long detailDegrees, Eye eye) {
			return message
					.addData("action", "draw")
					.addData("character", OptotypeCharacterMapper.toString(character))
					.addData("detail", String.valueOf(detailDegrees))
					.addData("eye", eye.toString().toLowerCase());
		}
	}
}
