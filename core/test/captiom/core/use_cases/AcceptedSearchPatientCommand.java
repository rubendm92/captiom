package captiom.core.use_cases;

import captiom.core.model.Gender;
import captiom.core.model.Patient;
import captiom.core.model.PatientNotFound;
import org.junit.Test;

import java.time.LocalDate;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class AcceptedSearchPatientCommand {

	@Test
	public void should_return_patient_given_patient_id() {
		SearchPatientCommand command = new SearchPatientCommand();
		Patient patient = command.searchPatient("1111");
		assertThat(patient.id, is("1111"));
		assertThat(patient.dateOfBirth, is(LocalDate.of(1992, 6, 22)));
		assertThat(patient.gender, is(Gender.MALE));
	}

	@Test(expected = PatientNotFound.class)
	public void should_throw_patient_not_found_exception_when_patient_is_not_registered() {
		SearchPatientCommand command = new SearchPatientCommand();
		Patient patient = command.searchPatient("1113");
		assertThat(patient.id, is("1113"));
		assertThat(patient.dateOfBirth, is(LocalDate.of(1992, 10, 15)));
		assertThat(patient.gender, is(Gender.FEMALE));
	}
}
