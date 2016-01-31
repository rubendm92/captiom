package captiom.server.displays;

import captiom.core.model.device.CharacterHeightCalculator;
import captiom.core.model.device.Device;
import captiom.server.infrastructure.Services;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class DeviceDisplay implements Display {

	private final Services services;
	private final List<Consumer<String>> listeners = new ArrayList<>();
	private final CharacterHeightCalculator characterHeightCalculator = new CharacterHeightCalculator();

	public DeviceDisplay(Services services) {
		this.services = services;
	}

	@Override
	public void show() {
		services.pushService().notify("DeviceConfiguration");
	}

	public void onDeviceSelected(Consumer<String> listener) {
		listeners.add(listener);
	}

	public void configure(Configuration configuration) {
		configureCharacterHeightCalculator(configuration);
		listeners.stream().forEach(listener -> listener.accept(configuration.deviceId));
	}

	public CharacterHeightCalculator calculatorForDevice() {
		return characterHeightCalculator;
	}

	private void configureCharacterHeightCalculator(Configuration configuration) {
		characterHeightCalculator.usingDevice(device(configuration.deviceId))
				.withDiopters(configuration.diopters)
				.atDistance(configuration.distance);
	}

	private Device device(String deviceId) {
		return services.deviceService().get(deviceId);
	}

	public static class Configuration {
		public final String deviceId;
		public final double diopters;
		public final double distance;

		public Configuration(String deviceId, double diopters, double distance) {
			this.deviceId = deviceId;
			this.diopters = diopters;
			this.distance = distance;
		}
	}
}
