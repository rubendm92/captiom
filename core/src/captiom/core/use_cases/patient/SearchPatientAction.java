package captiom.core.use_cases.patient;

import captiom.core.model.patient.Patient;
import captiom.core.model.patient.PatientNotFound;
import captiom.core.model.patient.PatientService;

public class SearchPatientAction {

	private final PatientService service;

	public SearchPatientAction(PatientService service) {
		this.service = service;
	}

	public Patient searchPatient(String id) {
		Patient patient = service.get(id);
		if (patient == null)
			throw new PatientNotFound(id);
		return patient;
	}
}
