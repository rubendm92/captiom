package captiom.server;

import captiom.core.model.device.DeviceService;
import captiom.core.model.patient.PatientService;
import captiom.core.model.test.TestService;
import captiom.core.use_cases.device.GetDevicesAction;
import captiom.core.use_cases.device.RegisterDeviceAction;
import captiom.server.controllers.*;
import captiom.server.displays.ApplicationDisplay;
import captiom.server.infrastructure.*;

import static spark.Spark.*;
import static spark.SparkBase.staticFileLocation;

public class Application {

	public static void main(String[] args) {
		staticFileLocation("site");
		Services services = services(new PushService(8081), new DisplayService());
		services.pushService().addConnectionOpenedListener(() -> services.displayService().register(new ApplicationDisplay(services)));

		GetDevicesAction getDevicesAction = new GetDevicesAction(services.deviceService());
		RegisterDeviceAction registerDeviceAction = new RegisterDeviceAction(services.deviceService());

		post("/patient", new RegisterPatientController(services.displayService()));
		get("/devices", new GetDevicesController(getDevicesAction));
		put("/devices", new RegisterDeviceController(registerDeviceAction));
		post("/device", new ConfigureDeviceController(services.displayService()));
		post("/test", new TestController(services.displayService()));
	}

	private static Services services(PushService pushService, DisplayService displayService) {
		return new Services() {

			private final PatientService patientService = createPatientService();
			private final DeviceService deviceService = createDeviceService();
			private final TestService testService = new TestService();

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

	private static PatientService createPatientService() {
		return new PatientService(patient -> {
		});
	}

	private static DeviceService createDeviceService() {
		return new DeviceService(new InMemoryDeviceRepository(), new AndroidDeviceNotifier());
	}
}
