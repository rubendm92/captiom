package captiom.server;

import captiom.core.model.device.DeviceService;
import captiom.core.model.patient.PatientService;
import captiom.core.model.test.TestService;
import captiom.server.infrastructure.DisplayService;
import captiom.server.infrastructure.PushService;
import captiom.server.infrastructure.Services;
import captiom.server.infrastructure.repositories.AndroidDeviceNotifier;
import captiom.server.infrastructure.repositories.CsvPatientRepository;
import captiom.server.infrastructure.repositories.CsvTestRepository;
import captiom.server.infrastructure.repositories.InMemoryDeviceRepository;

class ApplicationServices implements Services {

	private final PushService pushService;
	private final DisplayService displayService = new DisplayService();
	private final DeviceService deviceService = createDeviceService();
	private final PatientService patientService;
	private final TestService testService;

	public ApplicationServices(PushService pushService, String workingDirectory) {
		this.pushService = pushService;
		patientService = createPatientService(workingDirectory);
		testService = createTestService(workingDirectory);
	}

	@Override
	public PushService pushService() {
		return pushService;
	}

	@Override
	public DisplayService displayService() {
		return displayService;
	}

	@Override
	public PatientService patientService() {
		return patientService;
	}

	@Override
	public DeviceService deviceService() {
		return deviceService;
	}

	@Override
	public TestService testService() {
		return testService;
	}

	private static PatientService createPatientService(String workingDirectory) {
		return new PatientService(new CsvPatientRepository(workingDirectory));
	}

	private static DeviceService createDeviceService() {
		return new DeviceService(new InMemoryDeviceRepository(), new AndroidDeviceNotifier());
	}

	private static TestService createTestService(String workingDirectory) {
		return new TestService(new CsvTestRepository(workingDirectory));
	}
}
