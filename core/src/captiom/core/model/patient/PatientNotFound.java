package captiom.core.model.patient;

public class PatientNotFound extends RuntimeException {

	public PatientNotFound(String patientId) {
		super("Patient with id " + patientId + " was not found");
	}
}
