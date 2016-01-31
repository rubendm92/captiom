package captiom.core.actions.patient;

import captiom.core.model.patient.Patient;
import captiom.core.model.patient.PatientService;
import org.junit.Test;

import static captiom.core.model.patient.Gender.MALE;
import static java.time.LocalDate.now;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class AcceptedRegisterPatientAction {

	@Test
	public void should_save_patient() {
		PatientService service = mock(PatientService.class);
		RegisterPatientAction action = new RegisterPatientAction(service);

		action.register(new Patient("1111", "Rubne", now(), MALE));

		verify(service).save(new Patient("1111", "Rubne", now(), MALE));
	}
}
