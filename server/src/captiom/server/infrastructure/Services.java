package captiom.server.infrastructure;

import captiom.core.model.device.DeviceService;
import captiom.core.model.patient.PatientService;
import captiom.core.model.test.TestService;

public interface Services {

	PushService pushService();

	DisplayService displayService();

	PatientService patientService();

	DeviceService deviceService();

	TestService testService();
}
