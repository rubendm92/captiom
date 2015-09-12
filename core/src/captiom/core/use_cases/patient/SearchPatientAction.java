package captiom.core.use_cases.patient;

import captiom.core.model.patient.Patient;
import captiom.core.model.patient.PatientNotFound;
import captiom.core.model.patient.PatientService;

import java.util.Optional;

public class SearchPatientAction {

	private final PatientService service;

	public SearchPatientAction(PatientService service) {
		this.service = service;
	}

	public Patient searchPatient(String id) {
		Optional<Patient> patient = service.get(id);
		if (!patient.isPresent())
			throw new PatientNotFound(id);
		return patient.get();
	}
}
