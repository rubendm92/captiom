package captiom.server;

import captiom.core.infrastructure.device.DeviceRepository;
import captiom.core.model.device.Device;
import captiom.core.model.device.DeviceService;
import captiom.core.model.patient.PatientService;
import captiom.core.use_cases.device.GetDevicesAction;
import captiom.core.use_cases.patient.RegisterPatientAction;
import captiom.server.controllers.GetDevicesController;
import captiom.server.controllers.RegisterPatientController;
import captiom.server.infrastructure.PushService;

import java.util.List;

import static java.util.Arrays.asList;
import static spark.Spark.get;
import static spark.Spark.post;
import static spark.SparkBase.staticFileLocation;

public class Application {

	public static void main(String[] args) {
		staticFileLocation("site");
		PushService pushService = new PushService(8081);
		PatientService patientService = new PatientService(patient -> {
		});

		GetDevicesAction getDevicesAction = new GetDevicesAction(new DeviceService(new DeviceRepository() {
			@Override
			public void save(Device device) {
			}

			@Override
			public List<Device> all() {
				return asList(new Device("1").modelName("Nexus 5"), new Device("2").modelName("LG G2"), new Device("3").modelName("Nexus 5X"), new Device("4").modelName("LG G2"), new Device("5").modelName("Nexus 5X"));
			}
		}, null));
		get("/devices", new GetDevicesController(getDevicesAction));
		post("/device", (req, res) -> "OK");
		post("/patient", new RegisterPatientController(new RegisterPatientAction(patientService), pushService));
	}
}
