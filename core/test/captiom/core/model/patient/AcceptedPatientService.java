package captiom.core.model.patient;

import captiom.core.infrastructure.patient.PatientRepository;
import org.junit.Test;

import java.util.Optional;

import static java.time.LocalDate.of;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class AcceptedPatientService {

	@Test
	public void should_return_patient_from_repository() {
		String patientId = "4444";
		Patient patient = new Patient(patientId, of(1992, 6, 22), Gender.MALE);
		PatientRepository repository = mock(PatientRepository.class);
		when(repository.find(patientId)).thenReturn(Optional.of(patient));

		PatientService service = new PatientService(repository);
		assertThat(service.get(patientId).get(), is(patient));
	}

	@Test
	public void should_return_empty_optional_when_patient_is_not_in_repository() {
		String patientId = "4444";
		PatientRepository repository = mock(PatientRepository.class);
		when(repository.find(patientId)).thenReturn(Optional.empty());

		PatientService service = new PatientService(repository);
		assertThat(service.get(patientId).isPresent(), is(false));
	}

	@Test
	public void should_save_patient_into_repository() {
		String patientId = "4444";
		Patient patient = new Patient(patientId, of(1992, 6, 22), Gender.MALE);
		PatientRepository repository = mock(PatientRepository.class);

		PatientService service = new PatientService(repository);
		service.save(patient);
		verify(repository).save(patient);
	}
}