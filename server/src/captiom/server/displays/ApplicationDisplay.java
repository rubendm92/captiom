package captiom.server.displays;

import captiom.server.infrastructure.Services;

public class ApplicationDisplay implements Display {

	private final Services services;

	public ApplicationDisplay(Services services) {
		this.services = services;
		services.displayService().register(new PatientFormDisplay(services));
		patientFormDisplay().onPatientRegistered(this::showDeviceConfiguration);
		show();
	}

	@Override
	public void show() {
		patientFormDisplay().show();
	}

	private void showDeviceConfiguration() {
		services.displayService().register(new DeviceDisplay(services));
		deviceDisplay().show();
		deviceDisplay().onDeviceSelected(this::showTest);
	}

	private void showTest(String deviceId) {
		services.displayService().register(new TestDisplay(patientFormDisplay().patient(),
				deviceDisplay().calculatorForDevice(), deviceId, services));
		testDisplay().show();
	}

	private PatientFormDisplay patientFormDisplay() {
		return display(PatientFormDisplay.class);
	}

	private DeviceDisplay deviceDisplay() {
		return display(DeviceDisplay.class);

	}private TestDisplay testDisplay() {
		return display(TestDisplay.class);
	}

	private <T extends Display> T display(Class<T> type) {
		return services.displayService().display(type);
	}
}
