package captiom.core.model.patient;

import captiom.core.infrastructure.patient.PatientRepository;

public class PatientService {

	private final PatientRepository repository;

	public PatientService(PatientRepository repository) {
		this.repository = repository;
	}

	public Patient get(String id) {
		return repository.find(id);
	}

	public void save(Patient patient) {
		throw new UnsupportedOperationException("Not implemented yet");
	}
}
