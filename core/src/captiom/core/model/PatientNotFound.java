package captiom.core.model;

public class PatientNotFound extends RuntimeException {

	public PatientNotFound(String patientId) {
		super("Patient with id " + patientId + " was not found");
	}
}
