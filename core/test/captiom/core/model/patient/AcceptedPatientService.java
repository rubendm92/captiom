package captiom.core.model.patient;

import captiom.core.infrastructure.patient.PatientRepository;
import org.junit.Test;

import static java.time.LocalDate.of;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class AcceptedPatientService {

	@Test
	public void should_return_patient_from_repository() {
		PatientRepository repository = mock(PatientRepository.class);
		String patientId = "4444";
		Patient patient = new Patient(patientId, of(1992, 6, 22), Gender.MALE);
		PatientService service = new PatientService(repository);
		service.get(patientId);
		when(repository.find(patientId)).thenReturn(patient);
	}
}