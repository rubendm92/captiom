package captiom.core.use_cases.patient;

import captiom.core.model.patient.Patient;
import captiom.core.model.patient.PatientNotFound;
import captiom.core.model.patient.PatientService;
import org.junit.Test;

import static captiom.core.model.patient.Gender.MALE;
import static java.time.LocalDate.of;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class AcceptedSearchPatientAction {

	@Test
	public void should_return_patient_given_patient_id() {
		Patient patient = action(service("1111", new Patient("1111", of(1992, 6, 22), MALE))).searchPatient("1111");
		assertThat(patient.id, is("1111"));
		assertThat(patient.dateOfBirth, is(of(1992, 6, 22)));
		assertThat(patient.gender, is(MALE));
	}

	@Test(expected = PatientNotFound.class)
	public void should_throw_patient_not_found_exception_when_patient_is_not_registered() {
		action(service("1113", null)).searchPatient("1113");
	}

	private SearchPatientAction action(PatientService service) {
		return new SearchPatientAction(service);
	}

	private PatientService service(String id, Patient result) {
		PatientService service = mock(PatientService.class);
		when(service.get(id)).thenReturn(result);
		return service;
	}
}
