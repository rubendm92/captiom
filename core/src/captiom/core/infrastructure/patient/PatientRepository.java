package captiom.core.infrastructure.patient;

import captiom.core.model.patient.Patient;

import java.util.Optional;

public interface PatientRepository {

	Optional<Patient> find(String patientId);
}
