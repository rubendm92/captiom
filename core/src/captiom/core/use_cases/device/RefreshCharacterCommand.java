package captiom.core.use_cases.device;

import captiom.core.model.device.DeviceService;
import captiom.core.model.device.Eye;
import captiom.core.model.device.OptotypeCharacter;

public class RefreshCharacterCommand {

	private final DeviceService service;
	private String deviceId;
	private OptotypeCharacter character;
	private double height;
	private Eye eye;

	public RefreshCharacterCommand(DeviceService service) {
		this.service = service;
	}

	public CharacterReader using(String deviceId) {
		this.deviceId = deviceId;
		return this::storeCharacter;
	}

	private HeightReader storeCharacter(OptotypeCharacter character) {
		this.character = character;
		return this::storeHeight;
	}

	private EyeReader storeHeight(double height) {
		this.height = height;
		return this::refreshDevice;
	}

	private RefreshCharacterCommand refreshDevice(Eye eye) {
		this.eye = eye;
		service.using(deviceId).drawChar(character, height, eye);
		return this;
	}

	@FunctionalInterface
	public interface CharacterReader {
		HeightReader show(OptotypeCharacter character);
	}

	@FunctionalInterface
	public interface HeightReader {
		EyeReader withHeight(double height);
	}

	@FunctionalInterface
	public interface EyeReader {
		RefreshCharacterCommand in(Eye eye);
	}
}
