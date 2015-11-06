package captiom.server.infrastructure;

import captiom.core.model.device.DeviceService;
import captiom.core.model.patient.PatientService;

public interface Services {

	PushService pushService();
	PatientService patientService();
	DeviceService deviceService();
	DisplayService displayService();
}
