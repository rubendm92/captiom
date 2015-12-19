package captiom.core.use_cases.device;

import captiom.core.model.device.DeviceService;
import captiom.core.model.device.Eye;
import captiom.core.model.device.OptotypeCharacter;

public class RefreshCharacterAction {

	private final DeviceService service;
	private String deviceId;
	private OptotypeCharacter character;
	private long detailDegrees;
	private Eye eye;

	public RefreshCharacterAction(DeviceService service) {
		this.service = service;
	}

	public CharacterReader using(String deviceId) {
		this.deviceId = deviceId;
		return this::storeCharacter;
	}

	private DetailReader storeCharacter(OptotypeCharacter character) {
		this.character = character;
		return this::storeDetail;
	}

	private EyeReader storeDetail(long detailDegrees) {
		this.detailDegrees = detailDegrees;
		return this::refreshDevice;
	}

	private RefreshCharacterAction refreshDevice(Eye eye) {
		this.eye = eye;
		service.using(deviceId).drawChar(character, detailDegrees, eye);
		return this;
	}

	public void clear(String deviceId) {
		service.using(deviceId).clear();
	}

	@FunctionalInterface
	public interface CharacterReader {
		DetailReader show(OptotypeCharacter character);
	}

	@FunctionalInterface
	public interface DetailReader {
		EyeReader withDetail(long detailDegrees);
	}

	@FunctionalInterface
	public interface EyeReader {
		RefreshCharacterAction in(Eye eye);
	}
}
