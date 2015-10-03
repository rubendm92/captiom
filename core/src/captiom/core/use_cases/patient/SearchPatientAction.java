package captiom.core.use_cases.patient;

import captiom.core.model.patient.Patient;
import captiom.core.model.patient.PatientService;

import java.util.Optional;

public class SearchPatientAction {

	private final PatientService service;

	public SearchPatientAction(PatientService service) {
		this.service = service;
	}

	public Optional<Patient> searchPatient(String id) {
		return service.get(id);
	}
}
