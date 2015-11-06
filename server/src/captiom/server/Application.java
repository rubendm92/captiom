package captiom.server;

import captiom.core.infrastructure.device.DeviceRepository;
import captiom.core.model.device.Device;
import captiom.core.model.device.DeviceService;
import captiom.core.model.patient.PatientService;
import captiom.core.use_cases.device.GetDevicesAction;
import captiom.core.use_cases.device.RegisterDeviceAction;
import captiom.server.controllers.*;
import captiom.server.displays.ApplicationDisplay;
import captiom.server.infrastructure.DisplayService;
import captiom.server.infrastructure.PushService;
import captiom.server.infrastructure.Services;

import java.util.List;

import static java.util.Arrays.asList;
import static spark.Spark.get;
import static spark.Spark.post;
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
		post("/device", new DeviceController(new RegisterDeviceController(registerDeviceAction), new ConfigureDeviceController()));
	}

	private static Services services(PushService pushService, DisplayService displayService) {
		return new Services() {

			private final PatientService patientService = createPatientService();
			private final DeviceService deviceService = createDeviceService();

			@Override
			public PushService pushService() {
				return pushService;
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
			public DisplayService displayService() {
				return displayService;
			}
		};
	}

	private static PatientService createPatientService() {
		return new PatientService(patient -> {
		});
	}

	private static DeviceService createDeviceService() {
		return new DeviceService(new DeviceRepository() {
			@Override
			public void save(Device device) {
			}

			@Override
			public List<Device> all() {
				return asList(new Device("1").modelName("Nexus 5"), new Device("2").modelName("LG G2"), new Device("3").modelName("Nexus 5X"), new Device("4").modelName("LG G2"), new Device("5").modelName("Nexus 5X"));
			}
		}, null);
	}
}
