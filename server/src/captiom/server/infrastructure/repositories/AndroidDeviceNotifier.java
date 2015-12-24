package captiom.server.infrastructure.repositories;

import captiom.core.infrastructure.device.DeviceNotifier;
import captiom.core.model.device.Device;
import captiom.core.model.device.Eye;
import captiom.core.model.device.OptotypeCharacter;
import captiom.server.infrastructure.OptotypeCharacterMapper;

public class AndroidDeviceNotifier implements DeviceNotifier {

	@Override
	public DeviceLink device(Device device) {
		return new AndroidDeviceLink(device);
	}

	private class AndroidDeviceLink implements DeviceLink {

		private final Device device;

		public AndroidDeviceLink(Device device) {
			this.device = device;
		}

		@Override
		public void drawChar(OptotypeCharacter character, double charHeight, Eye eye) {
			GcmNotificationMessage
					.send(message -> parametersToDrawChar(message, character, charHeight, eye))
					.to(device.id());
		}

		@Override
		public void clear() {
			GcmNotificationMessage
					.send(sender -> sender.addData("action", "clear"))
					.to(device.id());
		}

		private GcmNotificationMessage parametersToDrawChar(GcmNotificationMessage message, OptotypeCharacter character, double charPixelsHeight, Eye eye) {
			return message
					.addData("action", "draw")
					.addData("character", OptotypeCharacterMapper.toString(character))
					.addData("charHeight", String.valueOf(charPixelsHeight))
					.addData("eye", eye.toString());
		}
	}
}
