package captiom.server;

import captiom.core.model.device.DeviceService;
import captiom.core.model.patient.PatientService;
import captiom.core.model.test.TestService;
import captiom.core.use_cases.device.GetDevicesAction;
import captiom.core.use_cases.device.RegisterDeviceAction;
import captiom.server.controllers.*;
import captiom.server.displays.ApplicationDisplay;
import captiom.server.infrastructure.DisplayService;
import captiom.server.infrastructure.PushService;
import captiom.server.infrastructure.Services;
import captiom.server.infrastructure.repositories.AndroidDeviceNotifier;
import captiom.server.infrastructure.repositories.CsvPatientRepository;
import captiom.server.infrastructure.repositories.CsvTestRepository;
import captiom.server.infrastructure.repositories.InMemoryDeviceRepository;

import static spark.Spark.*;
import static spark.SparkBase.staticFileLocation;

public class Application {

	public static void main(String[] args) {
		staticFileLocation("site");
		Services services = services(new PushService(8081), args[0]);
		services.pushService().addConnectionOpenedListener(() -> services.displayService().register(new ApplicationDisplay(services)));

		GetDevicesAction getDevicesAction = new GetDevicesAction(services.deviceService());
		RegisterDeviceAction registerDeviceAction = new RegisterDeviceAction(services.deviceService());

		post("/patient", new RegisterPatientController(services.displayService()));
		get("/devices", new GetDevicesController(getDevicesAction));
		put("/devices", new RegisterDeviceController(registerDeviceAction));
		post("/device", new ConfigureDeviceController(services.displayService()));
		post("/test", new TestController(services.displayService()));
	}

	private static Services services(PushService pushService, String workingDirectory) {
		return new Services() {

			private final PatientService patientService = createPatientService(workingDirectory);
			private final DeviceService deviceService = createDeviceService();
			private final TestService testService = createTestService(workingDirectory);
			private final DisplayService displayService = new DisplayService();

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
		};
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
