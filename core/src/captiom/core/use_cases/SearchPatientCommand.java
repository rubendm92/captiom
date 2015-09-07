package captiom.core.use_cases;

import captiom.core.model.Patient;
import captiom.core.model.PatientNotFound;
import captiom.core.model.PatientService;

public class SearchPatientCommand {

	private final PatientService service;

	public SearchPatientCommand(PatientService service) {
		this.service = service;
	}

	public Patient searchPatient(String id) {
		Patient patient = service.get(id);
		if (patient == null)
			throw new PatientNotFound(id);
		return patient;
	}
}
