package captiom.core.use_cases;

import captiom.core.model.patient.Patient;
import captiom.core.model.patient.PatientService;

public class RegisterPatientCommand {

	private final PatientService service;

	public RegisterPatientCommand(PatientService service) {
		this.service = service;
	}

	public void register(Patient patient) {
		service.save(patient);
	}
}
