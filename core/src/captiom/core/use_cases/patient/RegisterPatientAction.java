package captiom.core.use_cases.patient;

import captiom.core.model.patient.Patient;
import captiom.core.model.patient.PatientService;

public class RegisterPatientAction {

	private final PatientService service;

	public RegisterPatientAction(PatientService service) {
		this.service = service;
	}

	public void register(Patient patient) {
		service.save(patient);
	}
}
