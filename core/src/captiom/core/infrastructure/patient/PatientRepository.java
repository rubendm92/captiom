package captiom.core.infrastructure.patient;

import captiom.core.model.patient.Patient;

public interface PatientRepository {

	Patient find(String patientId);
}
