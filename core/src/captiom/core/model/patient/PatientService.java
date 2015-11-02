package captiom.core.model.patient;

import captiom.core.infrastructure.patient.PatientRepository;

public class PatientService {

	private final PatientRepository repository;

	public PatientService(PatientRepository repository) {
		this.repository = repository;
	}

	public void save(Patient patient) {
		repository.save(patient);
	}
}
