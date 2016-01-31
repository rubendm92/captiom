package captiom.core.model.patient;

public class PatientService {

	private final PatientRepository repository;

	public PatientService(PatientRepository repository) {
		this.repository = repository;
	}

	public void save(Patient patient) {
		repository.save(patient);
	}
}
