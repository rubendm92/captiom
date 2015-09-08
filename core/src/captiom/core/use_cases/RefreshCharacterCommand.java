package captiom.core.use_cases;

import captiom.core.model.device.DeviceService;
import captiom.core.model.device.OptotypeCharacter;

public class RefreshCharacterCommand {

	private final DeviceService service;
	private String deviceId;
	private OptotypeCharacter character;
	private double height;

	public RefreshCharacterCommand(DeviceService service) {
		this.service = service;
	}

	public CharacterReader using(String deviceId) {
		this.deviceId = deviceId;
		return this::storeCharacter;
	}

	private HeightReader storeCharacter(OptotypeCharacter character) {
		this.character = character;
		return this::refreshDevice;
	}

	private RefreshCharacterCommand refreshDevice(double height) {
		this.height = height;
		service.using(deviceId).drawChar(character, height);
		return this;
	}

	@FunctionalInterface
	public interface CharacterReader {
		HeightReader show(OptotypeCharacter character);
	}

	@FunctionalInterface
	public interface HeightReader {
		RefreshCharacterCommand withHeight(double height);
	}
}
