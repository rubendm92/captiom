package captiom.server.displays;

import captiom.server.infrastructure.Services;

public class ApplicationDisplay implements Display {

	private final Services services;

	public ApplicationDisplay(Services services) {
		this.services = services;
		services.displayService().register(new PatientFormDisplay(services));
		patientFormDisplay(services).onPatientRegistered(this::showDeviceConfiguration);
		show();
	}

	@Override
	public void show() {
		patientFormDisplay(services).show();
	}

	private void showDeviceConfiguration() {
		services.displayService().register(new DeviceConfigurationDisplay(services));
		services.displayService().display(DeviceConfigurationDisplay.class).show();
	}

	private PatientFormDisplay patientFormDisplay(Services services) {
		return services.displayService().display(PatientFormDisplay.class);
	}
}
