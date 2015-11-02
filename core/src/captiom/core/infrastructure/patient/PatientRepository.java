package captiom.core.infrastructure.patient;

import captiom.core.model.patient.Patient;

public interface PatientRepository {

	void save(Patient patient);
}
