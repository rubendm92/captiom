package captiom.core.model.patient;

import org.junit.Test;

import java.time.LocalDate;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class AcceptedPatientService {

	@Test
	public void should_save_patient_into_repository() {
		Patient patient = new Patient("4444", "Ruben", LocalDate.now(), Gender.MALE);
		PatientRepository repository = mock(PatientRepository.class);

		PatientService service = new PatientService(repository);
		service.save(patient);

		verify(repository).save(patient);
	}
}