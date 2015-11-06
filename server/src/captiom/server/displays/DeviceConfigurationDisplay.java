package captiom.server.displays;

import captiom.server.infrastructure.Services;

public class DeviceConfigurationDisplay implements Display {

	private final Services services;

	public DeviceConfigurationDisplay(Services services) {
		this.services = services;
	}

	@Override
	public void show() {
		services.pushService().notify("DeviceConfiguration");
	}
}
